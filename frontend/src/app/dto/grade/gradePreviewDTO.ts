import { IndexBookViewDTO } from '../book/indexBookViewDTO';

export interface GradePreviewDTO {
    book: IndexBookViewDTO;
    rating?: number;
    comment?: string;
}

