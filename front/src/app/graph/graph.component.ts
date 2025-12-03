import {
  Component,
  Input,
  Output,
  EventEmitter,
  OnInit,
  OnChanges,
  SimpleChanges,
  ViewChild,
  ElementRef,
} from "@angular/core";
import { CommonModule } from "@angular/common";
import { Attempt } from "../attempt/attempt.interface";

@Component({
  selector: "app-graph",
  standalone: true,
  imports: [CommonModule],
  templateUrl: "./graph.component.html",
  styleUrls: [],
})
export class GraphComponent implements OnInit, OnChanges {
  @Input() R: number = 0;
  @Input() attempts: Attempt[] = [];
  @Output() graphClick = new EventEmitter<{ x: number; y: number; r: number }>();
  @ViewChild("graph") graphElement!: ElementRef<SVGSVGElement>;

  viewBox: string = "0 0 500 500";
  svgSize: number = 500;
  center: number = 250;
  scaleFactor: number = 40;
  areaPath: string = "";

  ngOnInit(): void {
    this.areaPath = this.generateAreaPath(this.R);
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes["R"]) {
      this.areaPath = this.generateAreaPath(this.R);
    }
  }

  toSvgX(x: number): number {
    return this.center + x * this.scaleFactor;
  }

  toSvgY(y: number): number {
    return this.center - y * this.scaleFactor;
  }

  fromSvgX(svgX: number): number {
    return (svgX - this.center) / this.scaleFactor;
  }

  fromSvgY(svgY: number): number {
    return (this.center - svgY) / this.scaleFactor;
  }

  generateAreaPath(R: number): string {

    const p1 = `${this.toSvgX(-R)},${this.toSvgY(0)}`;
    const p2 = `${this.toSvgX(0)},${this.toSvgY(0)}`;
    const p3 = `${this.toSvgX(0)},${this.toSvgY(R)}`;
    const triangle = `M ${p1} L ${p2} L ${p3} Z`;

    const p4 = `${this.toSvgX(0)},${this.toSvgY(0)}`;
    const p5 = `${this.toSvgX(R/2)},${this.toSvgY(0)}`;
    const p6 = `${this.toSvgX(R/2)},${this.toSvgY(-R)}`;
    const p7 = `${this.toSvgX(0)},${this.toSvgY(-R)}`;
    const rect = `M ${p4} L ${p5} L ${p6} L ${p7} Z`;

    const rArc = Math.abs(this.scaleFactor * (R / 2));
    const arcStart = `${this.toSvgX(R/2)},${this.toSvgY(0)}`;
    const arcEnd = `${this.toSvgX(0)},${this.toSvgY(R/2)}`;
    const origin = `${this.toSvgX(0)},${this.toSvgY(0)}`;
    const arc = `M ${arcStart} A ${rArc} ${rArc} 0 0 0 ${arcEnd} L ${origin} Z`;

    return `${triangle} ${rect} ${arc}`;
  }

  handleGraphClick(event: MouseEvent) {
    if (this.R === 0) {
      console.warn("R = 0. Клик по графику игнорируется.");
      return;
    }

    const svgElement = this.graphElement.nativeElement;
    const CTM = svgElement.getScreenCTM();
    if (!CTM) return;

    const svgX = (event.clientX - CTM.e) / CTM.a;
    const svgY = (event.clientY - CTM.f) / CTM.d;

    const xCoord = this.fromSvgX(svgX);
    const yCoord = this.fromSvgY(svgY);

    const x = parseFloat(xCoord.toFixed(2));
    const y = parseFloat(yCoord.toFixed(2));
    const r = this.R;

    this.graphClick.emit({ x, y, r });
  }
}
