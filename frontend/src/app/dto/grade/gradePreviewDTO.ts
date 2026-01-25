import {IndexBookViewDTO} from "../book/indexBookViewDTO";

export interface GradePreviewDTO {
    id: number;
    book: IndexBookViewDTO;
    rating: number;
    comment: string;
}

