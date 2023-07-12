import { Injectable } from '@angular/core';
import { HttpClient ,HttpParams} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';


@Injectable({
  providedIn: 'root'
})
export class GptService {

  constructor(private http: HttpClient) { }



  traderChatWithGpt(message: string): Observable<any> {
    // TODO: Replace with your actual API URL
    const apiUrl = 'api/v1/bwic-service/bwics/chat';

    // Initialize Params Object
    let params = new HttpParams();

    // Begin assigning parameters
    params = params.append('message', message);
  
    // Call your API
    return this.http.get(apiUrl, { params: params, responseType: 'text' }).
    pipe(
      catchError(err => {
        console.error('API call failed:', err);
        return throwError(err);
      })
    );
  }
  

}
