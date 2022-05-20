import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

import { ICandidatoResponse } from 'src/app/interfaces/candidato-response';
import { ICargoResponse } from 'src/app/interfaces/cargo-response';
import { IEleitorResponse } from 'src/app/interfaces/eleitor-response';
import { IVotoRequest } from 'src/app/interfaces/voto-request';
import { CandidatosService } from 'src/app/services/candidatos.service';
import { CargosService } from 'src/app/services/cargos.service';
import { EleitoresService } from 'src/app/services/eleitores.service';

@Component({
  selector: 'app-votar',
  templateUrl: './votar.component.html',
  styleUrls: ['./votar.component.css']
})
export class VotarComponent implements OnInit {

  idEleitor = 0;
  eleitor: IEleitorResponse | null = null;

  cargos: ICargoResponse[] = [];
  cargoAtual!: ICargoResponse;

  candidatos: ICandidatoResponse[] = [];
  candidatosDoCargo: ICandidatoResponse[] = [];
  candidatoAtual: ICandidatoResponse | null | undefined = null;

  numeroAtual: string = '';

  countCargos: number = 0;
  countNumero: number = 0;

  selecionado = 0;
  digito1 = -1;
  digito2 = -1;
  digito3 = -2;
  digito4 = -2;
  digito5 = -2;

  formulario: FormGroup = new FormGroup({
    idCandidato: new FormControl(0, Validators.required)
  })

  constructor(
    private activedRoute: ActivatedRoute,
    private eleitoresService: EleitoresService,
    private cargosService: CargosService,
    private candidatosService: CandidatosService
  ) { }

  ngOnInit(): void {
    this.idEleitor = Number(this.activedRoute.snapshot.paramMap.get('id'));
    this.buscarDados();
    setTimeout(()=>{this.votacao()}, 2000);
  }

  buscarDados() {
    this.eleitoresService.buscar(this.idEleitor, '', '', '').subscribe((result) => {
      this.eleitor = result[0];
    });
    this.cargosService.buscar('', '', true).subscribe((result) => {
      this.cargos = result;
    });
    this.candidatosService.buscar('', '', '', '', '', '', true).subscribe((result) => {
      this.candidatos = result;
    });
  }

  votacao() {
    this.cargoAtual = this.cargos[this.countCargos];
    this.candidatosDoCargo = [];
    this.candidatos.filter((c) => {
      if(c.cargo.id === this.cargoAtual.id) {
        this.candidatosDoCargo.push(c);
      }
    });
    console.log(this.cargos)
    this.countNumero = this.candidatosDoCargo[this.countCargos].numero.toString().length;

    if(this.countNumero === 2) {
      this.digito3 = -2
    }
    if(this.countNumero === 3) {
      this.digito3 = -1
    }
    if(this.countNumero === 4) {
      this.digito3 = -1
      this.digito4 = -1
    }
    if(this.countNumero === 5) {
      this.digito5 = -1
    }
  }

  apertado(numero: number) {
    if(this.digito1 === -1 && this.selecionado === 0) {
      this.digito1 = numero;
      this.selecionado += 1;
    } else if(this.digito1 !== -1 && this.selecionado === 1) {
      this.digito2 = numero;
      this.selecionado +=1;
    } else if(this.digito2 !== -1 && this.selecionado === 2) {
      this.digito3 = numero;
      this.selecionado +=1;
    } else if(this.digito3 !== -1 && this.selecionado === 3) {
      this.digito4 = numero;
      this.selecionado +=1;
    } else if(this.digito4 !== -1 && this.selecionado === 4) {
      this.digito5 = numero;
      this.selecionado +=1;
    }

    if(this.selecionado === this.countNumero && this.digito2 !== -1 &&  this.digito3 === -2  &&  this.digito4 === -2 &&  this.digito5 === -2) {
      this.numeroAtual = this.digito1.toString() + this.digito2.toString();
      this.candidatosDoCargo.filter((c) => {
        if(c.numero === parseFloat(this.numeroAtual)){
          this.candidatoAtual = c;
        }
      });
    }
    if(this.selecionado === this.countNumero && this.digito3 !== -1  &&  this.digito4 === -2 &&  this.digito5 === -2) {
      this.numeroAtual = this.digito1.toString() + this.digito2.toString() + this.digito3.toString();
      this.candidatosDoCargo.filter((c) => {
        if(c.numero === parseFloat(this.numeroAtual)){
          this.candidatoAtual = c;
        }
      });
    }
    if(this.selecionado === this.countNumero && this.digito4 !== -1 &&  this.digito5 === -2) {
      this.numeroAtual = this.digito1.toString() + this.digito2.toString() + this.digito3.toString() + this.digito4.toString();
      this.candidatosDoCargo.filter((c) => {
        if(c.numero === parseFloat(this.numeroAtual)){
          this.candidatoAtual = c;
        }
      });
    }
    if(this.selecionado === this.countNumero && this.digito5 !== -1) {
      this.numeroAtual = this.digito1.toString() + this.digito2.toString() + this.digito3.toString() + this.digito4.toString() + this.digito5.toString();
      this.candidatosDoCargo.filter((c) => {
        if(c.numero === parseFloat(this.numeroAtual)){
          this.candidatoAtual = c;
        }
      });
    }
    new Audio('assets/audio/click.mp3').play();
  }

  tecla(tipo: string) {
    if(tipo === 'branco') {
      const voto: IVotoRequest = {
        idEleitor: this.idEleitor,
        idCargo: this.cargoAtual.id,
        idCandidato: 0,
        emBranco: true,
        nulo: false,
      }
      new Audio('assets/audio/votado.mp3').play();
      this.eleitoresService.votar(voto).subscribe(() => {});
      this.countCargos += 1;
      this.zerarNumeros();
      this.candidatoAtual = null;
      this.selecionado = 0;
      this.votacao();
    }

    if(tipo === 'confirma') {
      let voto: IVotoRequest = {
        idEleitor: this.idEleitor,
        idCargo: this.cargoAtual.id,
        idCandidato: 0,
        emBranco: false,
        nulo: false,
      }
      if (this.candidatoAtual !== null) {
        voto.idCandidato = this.candidatoAtual?.id as number;
      } else {
        voto.nulo = true;
      }
      new Audio('assets/audio/votado.mp3').play();
      this.eleitoresService.votar(voto).subscribe(() => {});
      this.countCargos += 1;
      this.zerarNumeros();
      this.candidatoAtual = null;
      this.selecionado = 0;
      this.votacao();
    }

    if(tipo === 'corrige') {
      this.zerarNumeros()
      this.candidatoAtual = null;
      this.selecionado = 0;
    }
  }

  zerarNumeros() {
    if(this.countNumero === 2) {
      this.digito1 = -1;
      this.digito2 = -1;
    }
    if(this.countNumero === 3) {
      this.digito1 = -1;
      this.digito2 = -1;
      this.digito3 = -1;
    }
    if(this.countNumero === 4) {
      this.digito1 = -1;
      this.digito2 = -1;
      this.digito3 = -1;
      this.digito4 = -1;
    }
    if(this.countNumero === 5) {
      this.digito1 = -1;
      this.digito2 = -1;
      this.digito3 = -1;
      this.digito4 = -1;
      this.digito5 = -1;
    }
  }
}
