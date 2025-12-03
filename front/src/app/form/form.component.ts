import { Component, EventEmitter, Input, OnInit, Output } from "@angular/core";
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from "@angular/forms";
import { CommonModule } from "@angular/common";

import { MatInputModule } from "@angular/material/input";
import { MatButtonModule } from "@angular/material/button";
import { MatFormFieldModule } from "@angular/material/form-field";

@Component({
  selector: "app-form",
  standalone: true,
  imports: [
    ReactiveFormsModule,
    CommonModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
  ],
  templateUrl: "./form.component.html",
  styleUrls: ["../styles/app.css"],
})
export class FormComponent implements OnInit {
  userDataForm!: FormGroup;

  @Output() submitData = new EventEmitter<any>();
  @Output() clearData = new EventEmitter<void>();
  @Output() rValueChange = new EventEmitter<number>();

  @Input() set initialR(value: number) {
    if (this.userDataForm) {
      this.userDataForm.get("r")?.setValue(value, { emitEvent: false });
    }
  }

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
      r: new FormControl(1, [
        Validators.required,
        Validators.min(-5),
        Validators.max(3)
      ]),
    });

    this.userDataForm.get("r")?.valueChanges.subscribe(r => {
      this.rValueChange.emit(r);
    });
  }

  onSubmit() {
    if (this.userDataForm.valid) {
      this.submitData.emit(this.userDataForm.value);
    } else {
      console.error("Форма содержит ошибки валидации.");
    }
  }

  onClear() {
    this.clearData.emit();
  }

  patchValues(x: number, y: number, r: number) {
    this.userDataForm.patchValue({ x, y, r });
  }

  get rControl(): FormControl {
    return this.userDataForm.get("r") as FormControl;
  }
}
