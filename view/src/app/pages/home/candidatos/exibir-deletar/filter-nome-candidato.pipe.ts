import { Pipe, PipeTransform } from '@angular/core';

import { ICandidatoResponse } from 'src/app/interfaces/candidato-response';

@Pipe({ name: 'filterNomeCandidato'})
export class FilterNomeCandidato implements PipeTransform {

    transform(candidatos: ICandidatoResponse[], descriptionQuery: string) {
        descriptionQuery = descriptionQuery
            .trim()
            .toUpperCase();

        if(descriptionQuery) {
            return candidatos.filter(candidato =>
                candidato.nome.includes(descriptionQuery)
            );
        } else {
            return candidatos;
        }
    }

}
