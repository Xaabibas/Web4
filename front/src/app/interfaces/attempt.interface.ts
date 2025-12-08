export interface Attempt {
  point: Point;
  result: boolean;
  start: string;
  workTime: number;
}

interface Point {
  x: number;
  y: number;
  r: number;
}

export interface GraphPoint {
  x: number;
  y: number;
  result: boolean;
}
