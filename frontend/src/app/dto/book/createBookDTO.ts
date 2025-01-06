export interface CreateBookDTO {
  name: string;
  authorId: number;
  genreIds: Set<number>;
  annotation: string;
  bookTypeId: number;
}
