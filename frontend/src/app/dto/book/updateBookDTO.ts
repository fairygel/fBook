export interface UpdateBookDTO {
  name: string|null;
  authorId: number|null;
  genreIds: number[];
  bookStatusId: number|null;
  startedReadDate: string|null;
  endedReadDate: string|null;
  annotation: string|null;
  bookTypeId: number|null;
}
