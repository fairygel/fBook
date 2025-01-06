import { Component } from '@angular/core';

@Component({
  selector: 'books-root',
  standalone: true,
  imports: [],
  template: `
    <p>
      book works!
    </p>
    <p>
      go to <a href="/authors">authors</a>
    </p>
  `,
  styles: ``
})
export class BookComponent {

}
