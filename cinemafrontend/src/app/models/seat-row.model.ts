import {SeatModel} from "./seat.model";

export class SeatRowModel {

  private _id: number | undefined;
  private _seatRow: number | undefined;
  private _seats: SeatModel[] | undefined;


  get id(): number | undefined {
    return this._id;
  }

  set id(value: number | undefined) {
    this._id = value;
  }

  get seatRow(): number | undefined {
    return this._seatRow;
  }

  set seatRow(value: number | undefined) {
    this._seatRow = value;
  }

  get seats(): SeatModel[] | undefined {
    return this._seats;
  }

  set seats(value: SeatModel[] | undefined) {
    this._seats = value;
  }
}
