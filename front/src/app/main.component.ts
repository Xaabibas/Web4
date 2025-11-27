import { Component, OnInit } from "@angular/core";
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from "@angular/forms";
import { HttpClient } from "@angular/common/http";
import { CommonModule } from '@angular/common';

import { Attempt } from "./attempt.interface";


@Component({
  selector: "main-page",
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: "./main.component.html",
  styleUrls: ["./styles/app.css"]
})
export class MainComponent implements OnInit {
  userDataForm!: FormGroup;
  attempts: Attempt[] = [];

  viewBox: string = '0 0 500 500';
  svgSize: number = 500;
  center: number = 250;
  scaleFactor: number = 40;
  rValue: number = 0;
  areaPath: string = '';

  private apiURL = "http://localhost:8080/check"
  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.userDataForm = new FormGroup({
      x: new FormControl(0, [
        Validators.required,
        Validators.min(-5),
        Validators.max(3)
      ]),
      y: new FormControl(0, [
        Validators.required,
        Validators.min(-5),
        Validators.max(5)
      ]),
      r: new FormControl(0, [
        Validators.required,
        Validators.min(-5),
        Validators.max(3)
      ]),
    });
    this.userDataForm.get('r')?.valueChanges.subscribe(r => {
      this.rValue = r;
      this.areaPath = this.generateAreaPath(r);
    });

    this.rValue = this.userDataForm.get('r')?.value;
    this.areaPath = this.generateAreaPath(this.rValue);
  }

  onSubmit() {
    if (this.userDataForm.valid) {
        this.sendData(this.userDataForm.value);
      } else {
        console.error("Форма содержит ошибки валидации.");
      }
  }

  sendData(data: any) {
    this.http.post(this.apiURL, data).subscribe({
      next: (response: any) => {
        this.attempts = [response, ...this.attempts];
      },
      error: (error: any) => {
        console.error("Ошибка при отправке данных:", error);
      }
    });
  }
  generateAreaPath(R: number): string {
    if (R <= 0) return '';

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
    const arc = `M ${this.toSvgX(R/2)},${this.toSvgY(0)} A ${rArc} ${rArc} 0 0 0 ${this.toSvgX(0)},${this.toSvgY(R/2)} L ${this.toSvgX(0)},${this.toSvgY(0)} Z`;

    return `${triangle} ${rect} ${arc}`;
  }

  toSvgX(x: number): number {
    return this.center + (x * this.scaleFactor);
  }

  toSvgY(y: number): number {
    return this.center - (y * this.scaleFactor);
  }
}
