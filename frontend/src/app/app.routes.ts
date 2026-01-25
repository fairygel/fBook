import {RouterModule, Routes} from '@angular/router';
import {NgModule} from "@angular/core";

import {BookComponent} from "./component/book/book-index/book.component";
import {NotFoundComponent} from "./component/not-found/not-found.component";
import {BookInfoComponent} from "./component/book/book-info/book-info.component";

export const routes: Routes = [
  {path: 'books/:id', component: BookInfoComponent},
  {path: '', component: BookComponent},
  {path: '**', component: NotFoundComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
