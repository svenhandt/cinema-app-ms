import {SeatRowModel} from "./seat-row.model";

export class RoomModel {

  private _id: number | undefined;
  private _roomName: string | undefined;
  private _seatRows: SeatRowModel[] | undefined;


  get id(): number | undefined {
    return this._id;
  }

  set id(value: number | undefined) {
    this._id = value;
  }

  set roomName(value: string | undefined) {
    this._roomName = value;
  }

  set seatRows(value: SeatRowModel[] | undefined) {
    this._seatRows = value;
  }

  get roomName(): string | undefined {
    return this._roomName;
  }

  get seatRows(): SeatRowModel[] | undefined {
    return this._seatRows;
  }
}
