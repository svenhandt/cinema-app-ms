

export class PresentationModel {

  private _id: string | undefined;
  private _filmName: string | undefined;
  private _weekDay: string | undefined;
  private _startTime: string | undefined;
  private _roomName: string | undefined;
  private _price: number | undefined;


  get id(): string | undefined {
    return this._id;
  }

  set id(value: string | undefined) {
    this._id = value;
  }

  get filmName(): string | undefined {
    return this._filmName;
  }

  set filmName(value: string | undefined) {
    this._filmName = value;
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

  get roomName(): string | undefined {
    return this._roomName;
  }

  set roomName(value: string | undefined) {
    this._roomName = value;
  }

  get price(): number | undefined {
    return this._price;
  }

  set price(value: number | undefined) {
    this._price = value;
  }
}
