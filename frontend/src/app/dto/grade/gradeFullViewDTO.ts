import {IndexBookViewDTO} from "../book/indexBookViewDTO";

export interface GradeFullViewDTO {
    book: IndexBookViewDTO;
    rating: number;
    comment: string;
}