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
