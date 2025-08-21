import { Component } from '@angular/core';
import {RouterOutlet} from "@angular/router";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    RouterOutlet
  ],
  template: `
    <router-outlet></router-outlet>
  `,
  styles: 'html, body { padding: 0; margin: 0;}'
})
export class AppComponent {

}
