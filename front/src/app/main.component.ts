import { Component, OnInit, ViewChild } from "@angular/core";
import { CommonModule } from "@angular/common";
import { PageEvent, MatPaginatorModule } from "@angular/material/paginator";
import { MatTableModule } from "@angular/material/table";
import { MatToolbarModule } from "@angular/material/toolbar";
import { MatButtonModule } from "@angular/material/button";
import { HttpClientModule } from "@angular/common/http";

import { AttemptService } from "./services/attempt.service";
import { AuthService } from "./services/auth.service";

import { Attempt, GraphPoint } from "./interfaces/attempt.interface";
import { GraphComponent } from "./graph/graph.component";
import { FormComponent } from "./form/form.component";

@Component({
  selector: "main-page",
  standalone: true,
  imports: [
    CommonModule,
    MatTableModule,
    MatPaginatorModule,
    MatToolbarModule,
    MatButtonModule,
    GraphComponent,
    FormComponent,
    HttpClientModule
  ],
  templateUrl: "./main.component.html",
  styleUrls: ["./styles/app.css"]
})
export class MainComponent implements OnInit {
  attempts: Attempt[] = [];
  graphPoints: GraphPoint[] = [];
  displayedColumns: string[] = ["x", "y", "r", "result", "start", "workTime"];

  @ViewChild(FormComponent) formComponent!: FormComponent;

  rValue: number = 1;

  pageSize: number = 10;
  pageIndex: number = 0;
  totalAttempts: number = 0;

  constructor(private attemptService: AttemptService, private authService: AuthService) {}

  ngOnInit(): void {
    this.loadAttempts();
    this.loadGraphPoints();
  }

  loadAttempts() {
    this.attemptService.loadAttempts(this.pageIndex, this.pageSize).subscribe({
      next: (response) => {
        this.attempts = response.content;
        this.totalAttempts = response.totalElements;
      },
      error: (error: any) => {
        console.error("Ошибка загрузки данных:", error);
      }
    });
  }

  loadGraphPoints() {
    this.attemptService.getGraphPoints().subscribe({
      next: (points) => {
        this.graphPoints = points;
      },
      error: (err) => console.error("Ошибка графика:", err)
    });
  }

  handlePageEvent(event: PageEvent) {
    this.pageSize = event.pageSize;
    this.pageIndex = event.pageIndex;
    this.loadAttempts();
  }

  handleSubmit(data: any) {
    this.sendData(data);
  }

  handleGraphClick(data: { x: number; y: number; r: number }) {
    this.formComponent.patchValues(data.x, data.y, this.rValue);
    this.sendData({ x: data.x, y: data.y, r: this.rValue });
  }

  handleRChange(r: number) {
    this.rValue = r;
  }

  sendData(data: any) {
    this.attemptService.sendAttempt(data).subscribe({
      next: () => {
        this.loadAttempts();
        this.loadGraphPoints();
      },
      error: (error: any) => {
        console.error("Ошибка при отправке данных:", error);
      }
    });
  }

  handleClear() {
    this.attemptService.clearAttempts().subscribe({
      next: () => {
        this.attempts = [];
        this.totalAttempts = 0;
      },
      error: (error: any) => {
        console.error("Ошибка при очистке данных:", error);
      }
    });
  }

  onLogout(): void {
    this.authService.logout();
  }
}
