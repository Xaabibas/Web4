import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: "main-page",
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './main.component.html',
  styleUrls: ['./styles/app.css']
})
export class MainComponent implements OnInit {
  userDataForm!: FormGroup;

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
  }

  onSubmit() {
    if (this.userDataForm.valid) {
        this.sendData(this.userDataForm.value);
      } else {
        console.error('Форма содержит ошибки валидации.');
      }
  }

  sendData(data: any) {
    this.http.post(this.apiURL, data).subscribe({
          next: (response: any) => {
            console.log('Данные успешно отправлены:', response);
          },
          error: (error: any) => {
            console.error('Ошибка при отправке данных:', error);
          }
        });
  }
}
