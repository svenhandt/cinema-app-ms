import {Component, OnDestroy, OnInit} from '@angular/core';
import {PresentationsService} from "../presentations.service";
import {Subscription} from "rxjs";
import {Router} from "@angular/router";
import {FilmModel} from "../models/film.model";
import {PresentationModel} from "../models/presentation.model";

@Component({
  selector: 'app-presentations',
  templateUrl: './presentations.component.html',
  styleUrls: ['./presentations.component.css']
})
export class PresentationsComponent implements OnInit, OnDestroy {

  filmModels: FilmModel[] = [];
  presentationsSubscription: Subscription | undefined;

  constructor(private presentationsService: PresentationsService, private router: Router) { }

  ngOnInit(): void {
    this.presentationsSubscription = this.presentationsService.fetchPresentations().subscribe(filmModels => {
      this.filmModels = filmModels;
    });
  }

  navigateToPresentation(presentationId: string | undefined) {
    if(presentationId != undefined) {
      this.router.navigate(['/presentationDetails'],
        {
          queryParams: {
            id: presentationId
          }
        });
    }
  }

  ngOnDestroy() {
    if(this.presentationsSubscription !== undefined) {
      this.presentationsSubscription.unsubscribe();
    }
  }

}
