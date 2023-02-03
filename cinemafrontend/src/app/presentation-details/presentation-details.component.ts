import {Component, OnDestroy, OnInit} from '@angular/core';
import {PresentationsService} from "../presentations.service";
import {Subscription} from "rxjs";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {BookingService} from "../booking.service";
import {PresentationModel} from "../models/presentation.model";
import {RoomModel} from "../models/room.model";
import {finalize} from "rxjs/operators";
import {SeatModel} from "../models/seat.model";

@Component({
  selector: 'app-presentation-details',
  templateUrl: './presentation-details.component.html',
  styleUrls: ['./presentation-details.component.css']
})
export class PresentationDetailsComponent implements OnInit, OnDestroy {

  id: string = '';
  presentation: PresentationModel | undefined;
  room: RoomModel | undefined;
  seatIdsForBooking: string[] = [];
  currentTotalforBooking: number = 0;
  paramsSubscription: Subscription | undefined;
  presentationSubscription: Subscription | undefined;
  roomSubscription: Subscription | undefined;

  constructor(private route: ActivatedRoute,
              private presentationsService: PresentationsService, private bookingService: BookingService, private router: Router) {
  }

  ngOnInit(): void {
    this.initRouteParams();
    this.initFromHttpAndSession();
  }

  initRouteParams() {
    this.paramsSubscription = this.route.queryParams.subscribe(
      (params: Params) => {
        this.id = params['id'];
      }
    );
  }

  initFromHttpAndSession() {
    this.presentationSubscription = this.presentationsService.fetchPresentation(this.id)
      .subscribe(presentation => {
        this.presentation = presentation;
        const roomId = this?.presentation?.roomName?.replace(' ', '_')?.toLowerCase();
        this.roomSubscription = this.presentationsService.fetchRoom(roomId)
          .subscribe(room => {
            this.room = room;
            this.initFromSessionStorage();
          });
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

  onAddSeatToBooking(seatId: string | undefined) {
    if (seatId != undefined) {
      this.seatIdsForBooking.push(seatId);
      this.adjustTotalPrice('ADD');
      this.saveInSessionStorage();
    }
  }

  onRemoveSeatFromBooking(seatIdToDelete: string | undefined) {
    this.seatIdsForBooking.forEach((currentSeatId, index) => {
      if (seatIdToDelete === currentSeatId) {
        this.seatIdsForBooking.splice(index, 1);
        this.adjustTotalPrice('SUBTRACT');
        this.saveInSessionStorage();
      }
    });
  }

  isInBooking(seatIdToCheck: string | undefined): boolean {
    let result = false;
    this.seatIdsForBooking.forEach((currentSeatId, index) => {
      if (seatIdToCheck === currentSeatId) {
        result = true;
      }
    });
    return result;
  }

  seatOccuppied(seat: SeatModel | undefined): boolean {
    let result = false;
    seat?.bookingIds?.forEach((bookingId) => {
      if(this.presentation?.id != undefined
        && bookingId.includes(this.presentation?.id)) {
        result = true;
      }
    });
    return result;
  }

  adjustTotalPrice(operation: string) {
    if (this.presentation?.price != undefined) {
      if ('ADD' === operation) {
        this.currentTotalforBooking = this.currentTotalforBooking + this.presentation?.price;
      } else if ('SUBTRACT' === operation) {
        this.currentTotalforBooking = this.currentTotalforBooking - this.presentation?.price;
      }
    }
  }

  onInitiateBooking() {
    this.router.navigate(['/bookingDataForm'],
      {
        queryParams: {
          presentationId: this.presentation?.id
        }
      });
  }

  ngOnDestroy() {
    this.paramsSubscription?.unsubscribe();
    this.presentationSubscription?.unsubscribe();
    this.roomSubscription?.unsubscribe();
  }

  saveInSessionStorage() {
    sessionStorage.setItem('bookingSeats_' + this.presentation?.id, this.seatIdsForBooking.toString());
    sessionStorage.setItem('bookingTotalPrice_' + this.presentation?.id, this.currentTotalforBooking.toString());
  }

}
