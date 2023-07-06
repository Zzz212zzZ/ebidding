import { Component,OnInit } from '@angular/core';
import { BehaviorSubject, Observable, filter } from 'rxjs';

@Component({
  selector: 'app-bidding',
  templateUrl: './bidding.component.html',
  styleUrls: ['./bidding.component.less']
})

export class BiddingComponent implements OnInit {
  ngOnInit(): void {
    const behaviorSubject = new BehaviorSubject(0);
    behaviorSubject.pipe(filter(x => x > 8)).subscribe((value) => {
      console.log('Observer: ' + value)
    });
    behaviorSubject.next(2);
    behaviorSubject.next(10);
    behaviorSubject.next(8);
  }

}