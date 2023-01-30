import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {environment} from "../environments/environment";
import {FilmModel} from "./models/film.model";
import {RoomModel} from "./models/room.model";
import {BehaviorSubject} from "rxjs";
import {PresentationModel} from "./models/presentation.model";

@Injectable({
  providedIn: 'root'
})
export class PresentationsService {

  constructor(private http: HttpClient) { }

  fetchPresentations() {
    return this.http.get<FilmModel[]>(environment.cinemaBaseUrl + 'presentationsms/films');
  }

  fetchPresentation(presentationId: string | undefined) {
    let params = new HttpParams();
    if(presentationId !== undefined) {
      params = params.append('presentationId', presentationId);
    }
    return this.http.get<PresentationModel>(environment.cinemaBaseUrl + 'presentationsms/films/presentations', {
      params: params
    });
  }

  fetchRoom(roomId: string | undefined) {
    let params = new HttpParams();
    if(roomId !== undefined) {
      params = params.append('roomId', roomId);
    }
    return this.http.get<RoomModel>(environment.cinemaBaseUrl + 'roomsms/rooms', {
      params: params
    });
  }

}
