export interface PartialErrorResponse {
    code: string;
    message: string;
}

export interface ValidationErrorResponse {
    code: string;
    message: string;
    detail: ValidationDetail[];
}

export interface ValidationDetail {
    field: string;
    value: string;
}

export type ApiError = PartialErrorResponse | ValidationErrorResponse;

export function isValidationError(error: ApiError): error is ValidationErrorResponse {
    return 'detail' in error;
}
