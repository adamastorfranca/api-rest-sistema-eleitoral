import { Pipe, PipeTransform } from '@angular/core';
import { IEleitorResponse } from 'src/app/interfaces/eleitor-response';


@Pipe({ name: 'filterNomeEleitor'})
export class FilterNomeEleitor implements PipeTransform {

    transform(eleitores: IEleitorResponse[], descriptionQuery: string) {
        descriptionQuery = descriptionQuery
            .trim()
            .toUpperCase();

        if(descriptionQuery) {
            return eleitores.filter(eleitor =>
                eleitor.nome.includes(descriptionQuery)
            );
        } else {
            return eleitores;
        }
    }

}
