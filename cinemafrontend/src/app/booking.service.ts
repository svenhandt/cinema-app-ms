import {Injectable} from '@angular/core';
import {BookingModel} from "./models/booking.model";
import {HttpClient, HttpParams} from "@angular/common/http";
import {environment} from "../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class BookingService {

  constructor(private http: HttpClient) { }

  createBookingInBackend(bookingCommand: any) {
    return this.http.post(environment.cinemaBaseUrl + 'ordersms/booking/save', bookingCommand);
  }

  fetchBookingConfirmation(id: string) {
    let params = new HttpParams();
    if(id !== undefined) {
      params = params.append('bookingId', id);
    }
    return this.http.get<BookingModel>(environment.cinemaBaseUrl + 'ordersms/booking/get', {
      params: params
    });
  }

}
