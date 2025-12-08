import { Injectable } from "@angular/core";
import { HttpClient, HttpParams } from "@angular/common/http";
import { Observable } from "rxjs";
import { map } from "rxjs/operators";
import { Attempt, GraphPoint } from "../interfaces/attempt.interface";

interface PaginatedAttempts {
  content: Attempt[];
  totalElements: number;
}

interface GraphResponse {
  content: GraphPoint[];
}

@Injectable({
  providedIn: "root"
})
export class AttemptService {

  private checkURL = "http://localhost:8080/api/check";
  private clearURL = "http://localhost:8080/api/clear";
  private selectURL = "http://localhost:8080/api/select";

  constructor(private http: HttpClient) { }

  loadAttempts(page: number, size: number): Observable<PaginatedAttempts> {
    const params = new HttpParams()
        .set("page", page.toString())
        .set("size", size.toString());
    return this.http.get<PaginatedAttempts>(this.selectURL, { params });
  }

  sendAttempt(data: any): Observable<any> {
    return this.http.post(this.checkURL, data);
  }

  clearAttempts(): Observable<any> {
    return this.http.delete(this.clearURL);
  }

  getGraphPoints(): Observable<GraphPoint[]> {
    return this.http.get<GraphResponse>(`${this.selectURL}/graph`)
      .pipe(map(res => res.content));
  }
}
