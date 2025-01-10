import {RouterModule, Routes} from '@angular/router';
import {NgModule} from "@angular/core";

import {AuthorComponent} from "./component/author/author.component";
import {BookComponent} from "./component/book/book.component";
import {NotFoundComponent} from "./component/not-found/not-found.component";
import {AuthorInfoComponent} from "./component/author/author-info/author-info.component";

export const routes: Routes = [
  {path: 'authors', component: AuthorComponent},
  {path: 'authors/:id', component: AuthorInfoComponent},
  {path: '', component: BookComponent},
  {path: '**', component: NotFoundComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
