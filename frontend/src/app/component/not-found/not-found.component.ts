import { Component } from '@angular/core';
import {Title} from "@angular/platform-browser";

@Component({
  selector: 'app-not-found',
  standalone: true,
  imports: [],
  template: `
    <p>
      404 page not found
    </p>
  `,
  styles: ``
})
export class NotFoundComponent {
  constructor(private readonly pageTitle: Title) {
    this.pageTitle.setTitle("404 :(");
  }
}
