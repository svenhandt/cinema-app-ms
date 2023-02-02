import {PresentationModel} from "./presentation.model";
import {SeatModel} from "./seat.model";

export class BookingModel {

  private _id: number | undefined;
  private _name: string | undefined;
  private _cardNo: string | undefined;
  private _filmName: string | undefined;
  private _roomName: string | undefined;
  private _weekDay: string | undefined;
  private _startTime: string | undefined;
  private _totalPrice: number | undefined;
  private _isValid: boolean | undefined;
  private _seats: SeatModel[] | undefined;


  get id(): number | undefined {
    return this._id;
  }

  set id(value: number | undefined) {
    this._id = value;
  }

  get name(): string | undefined {
    return this._name;
  }

  set name(value: string | undefined) {
    this._name = value;
  }

  get cardNo(): string | undefined {
    return this._cardNo;
  }

  set cardNo(value: string | undefined) {
    this._cardNo = value;
  }

  get filmName(): string | undefined {
    return this._filmName;
  }

  set filmName(value: string | undefined) {
    this._filmName = value;
  }

  get roomName(): string | undefined {
    return this._roomName;
  }

  set roomName(value: string | undefined) {
    this._roomName = value;
  }

  get weekDay(): string | undefined {
    return this._weekDay;
  }

  set weekDay(value: string | undefined) {
    this._weekDay = value;
  }

  get startTime(): string | undefined {
    return this._startTime;
  }

  set startTime(value: string | undefined) {
    this._startTime = value;
  }

  get totalPrice(): number | undefined {
    return this._totalPrice;
  }

  set totalPrice(value: number | undefined) {
    this._totalPrice = value;
  }

  get isValid(): boolean | undefined {
    return this._isValid;
  }

  set isValid(value: boolean | undefined) {
    this._isValid = value;
  }

  get seats(): SeatModel[] | undefined {
    return this._seats;
  }

  set seats(value: SeatModel[] | undefined) {
    this._seats = value;
  }

}
