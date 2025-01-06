export interface UpdateBookDTO {
  name: string;
  authorId: number;
  genreIds: Set<number>;
  bookStatusId: number;
  startedReadDate: string;
  endedReadDate: string;
  annotation: string;
  bookTypeId: number;
}
