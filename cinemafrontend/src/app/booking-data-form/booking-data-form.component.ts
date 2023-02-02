import {Component, OnDestroy, OnInit} from '@angular/core';
import {BookingService} from "../booking.service";
import {Subscription} from "rxjs";
import {NgForm} from "@angular/forms";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {PresentationModel} from "../models/presentation.model";
import {PresentationsService} from "../presentations.service";

@Component({
  selector: 'app-booking-data-form',
  templateUrl: './booking-data-form.component.html',
  styleUrls: ['./booking-data-form.component.css']
})
export class BookingDataFormComponent implements OnInit, OnDestroy {

  presentationId: string = '';
  presentation: PresentationModel | undefined;
  expiryMonths = ['01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12'];
  expiryYears: string[] = [];
  orderCreationError = false;
  seatIdsForBooking: string[] = [];
  currentTotalforBooking: number = 0;
  paramsSubscription: Subscription | undefined;
  presentationSubscription: Subscription | undefined;

  constructor(private route: ActivatedRoute,
              private presentationsService: PresentationsService,
              private bookingService: BookingService,
              private router: Router) { }

  ngOnInit(): void {
    this.initRouteParams();
    this.initFromHttpAndSession();
    this.initExpiryYears();
  }

  initRouteParams() {
    this.paramsSubscription = this.route.queryParams.subscribe(
      (params: Params) => {
        this.presentationId = params['presentationId'];
      }
    );
  }

  initFromHttpAndSession() {
    this.presentationSubscription = this.presentationsService.fetchPresentation(this.presentationId)
      .subscribe(presentation => {
        this.presentation = presentation;
        this.initFromSessionStorage();
      });
  }

  initFromSessionStorage() {
    const bookingSeatsAsStr = sessionStorage.getItem('bookingSeats_' + this.presentation?.id);
    const bookingTotalPriceAsStr = sessionStorage.getItem('bookingTotalPrice_' + this.presentation?.id);
    console.log('bookingSeats_' + this.presentation?.id + ' ' + bookingSeatsAsStr);
    if(bookingSeatsAsStr != null) {
      this.seatIdsForBooking = bookingSeatsAsStr.split(',');
    }
    if(bookingTotalPriceAsStr != null && !isNaN(Number(bookingTotalPriceAsStr))) {
      this.currentTotalforBooking = Number(bookingTotalPriceAsStr);
    }
  }

  getNameForReservedSeats() {
    let result = '';
    let seatsCount = this.seatIdsForBooking.length;
    if(seatsCount !== undefined && seatsCount > 1) {
      result = seatsCount + ' Plätze';
    }
    else if(seatsCount !== undefined && seatsCount === 1) {
      result = '1 Platz';
    }
    return result;
  }

  private initExpiryYears() {
    let currentYear = new Date().getFullYear();
    for(let i = currentYear; i < (currentYear + 10); i++) {
      this.expiryYears.push(i.toString());
    }
  }

  onSubmitBookingForm(form: NgForm) {
    if (!form.valid) {
      return;
    }
    const bookingCommand = this.createBookingCommand(form);
    this.bookingService.createBookingInBackend(bookingCommand).subscribe(
      (result: any) => {
        this.handleCreateBookingResult(result);
      }
    );
  }

  createBookingCommand(form: NgForm) {
    const bookingCommand = {
      presentationId: this.presentationId,
      seatIds: this.seatIdsForBooking,
      filmName: this.presentation?.filmName,
      roomName: this.presentation?.roomName,
      weekDay: this.presentation?.weekDay,
      startTime: this.presentation?.startTime,
      totalPrice: this.currentTotalforBooking,
      name: form.value.cardName,
      cardNo: form.value.cardNumber
    };
    return bookingCommand;
  }

  handleCreateBookingResult(result: any) {
    if(result['createBookingStatus'] === 'ERROR') {
      this.orderCreationError = true;
    }
    else if(result['createBookingStatus'] === 'OK') {
      this.orderCreationError = false;
      sessionStorage.clear();
      const bookingId = result['bookingId']
      this.router.navigate(['/bookingConfirmation/' + bookingId]);
    }
  }

  ngOnDestroy() {
    this.presentationSubscription?.unsubscribe();
    this.paramsSubscription?.unsubscribe();
  }

}
