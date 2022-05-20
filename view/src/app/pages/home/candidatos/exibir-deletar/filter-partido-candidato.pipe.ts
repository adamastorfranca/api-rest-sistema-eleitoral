import { Pipe, PipeTransform } from '@angular/core';

import { ICandidatoResponse } from 'src/app/interfaces/candidato-response';

@Pipe({ name: 'filterPartidoCandidato'})
export class FilterPartidoCandidato implements PipeTransform {

    transform(candidatos: ICandidatoResponse[], descriptionQuery: string) {
        descriptionQuery = descriptionQuery
            .trim()
            .toUpperCase();

        if(descriptionQuery) {
            return candidatos.filter(candidato =>
                candidato.legenda.includes(descriptionQuery)
            );
        } else {
            return candidatos;
        }
    }

}
