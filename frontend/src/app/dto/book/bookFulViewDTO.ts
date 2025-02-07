import {BookTypeIndexViewDTO} from "./type/bookTypeIndexViewDTO";
import {GradePreviewDTO} from "../grade/gradePreviewDTO";
import {BookStatusIndexViewDTO} from "./status/bookStatusIndexViewDTO";
import {GenreIndexViewDTO} from "../genre/genreIndexViewDTO";
import {AuthorIndexViewDTO} from "../author/authorIndexViewDTO";

export interface BookFullViewDTO {
  id: number;
  name: string;
  author: AuthorIndexViewDTO;
  genres: GenreIndexViewDTO[];
  bookStatus: BookStatusIndexViewDTO;
  startedReadDate: string;
  endedReadDate: string;
  annotation: string;
  bookType: BookTypeIndexViewDTO;
  grade: GradePreviewDTO;
}
