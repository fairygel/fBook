import {BookTypeDTO} from "./type/bookTypeDTO";
import {GradePreviewDTO} from "../grade/gradePreviewDTO";
import {BookStatusDTO} from "./status/bookStatusDTO";
import {GenreDTO} from "../genre/genreDTO";
import {AuthorIndexViewDTO} from "../author/authorIndexViewDTO";

export interface BookFullViewDTO {
  id: number;
  name: string;
  author: AuthorIndexViewDTO;
  genres: GenreDTO[];
  bookStatus: BookStatusDTO;
  startedReadDate: string;
  endedReadDate: string;
  annotation: string;
  bookType: BookTypeDTO;
  grade: GradePreviewDTO;
}
