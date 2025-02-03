export interface BookFullViewDTO {
  id: number;
  name: string;
  authorFirstName: string;
  authorLastName: string;
  genres: string[];
  bookStatus: string;
  startedReadDate: string;
  endedReadDate: string;
  annotation: string;
  bookType: string;
  gradeRating: number;
  gradeComment: string;
}
