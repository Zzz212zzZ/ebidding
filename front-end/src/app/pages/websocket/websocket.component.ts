import { ChangeDetectionStrategy, Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-websocket',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './websocket.component.html',
  styles: [
  ],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class WebsocketComponent {

}
