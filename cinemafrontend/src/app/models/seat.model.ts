

export class SeatModel {

  private _id: string | undefined;
  private _numberInSeatRow: number | undefined;
  private _seatType: string | undefined;


  get id(): string | undefined {
    return this._id;
  }

  set id(value: string | undefined) {
    this._id = value;
  }

  get numberInSeatRow(): number | undefined {
    return this._numberInSeatRow;
  }

  set numberInSeatRow(value: number | undefined) {
    this._numberInSeatRow = value;
  }

  get seatType(): string | undefined {
    return this._seatType;
  }

  set seatType(value: string | undefined) {
    this._seatType = value;
  }
}
