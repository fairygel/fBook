export interface CreateBookDTO {
  name: string;
  authorId: number;
  genreIds: number[];
  annotation: string|null;
  bookTypeId: number;
}
