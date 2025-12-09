export interface BookDTO {
  endedReadDate?: string;
  startedReadDate?: string;
  bookStatusId?: number;
  bookTypeId?: number;
  annotation?: string;
  genreIds?: number[];
  authorId?: number;
  name: string;
}

