import { Pipe, PipeTransform } from '@angular/core';

import { ICandidatoResponse } from 'src/app/interfaces/candidato-response';

@Pipe({ name: 'filterCargoCandidato'})
export class FilterCargoCandidato implements PipeTransform {

    transform(candidatos: ICandidatoResponse[], descriptionQuery: string) {
        descriptionQuery = descriptionQuery
            .trim()
            .toUpperCase();

        if(descriptionQuery) {
            return candidatos.filter(candidato =>
                candidato.cargo.nome.includes(descriptionQuery)
            );
        } else {
            return candidatos;
        }
    }

}
