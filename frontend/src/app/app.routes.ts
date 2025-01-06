import {RouterModule, Routes} from '@angular/router';
import {NgModule} from "@angular/core";

import {AuthorComponent} from "./component/author/author.component";
import {BookComponent} from "./component/book/book.component";
import {NotFoundComponent} from "./component/not-found/not-found.component";

export const routes: Routes = [
  {path: 'authors', component: AuthorComponent},
  {path: '', component: BookComponent},
  {path: '**', component: NotFoundComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
