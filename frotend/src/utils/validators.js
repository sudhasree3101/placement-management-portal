export const EMAIL_VALIDATION = {
  required: 'Email is required',
  pattern: {
    value: /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/,
    message: 'Invalid email address',
  },
};

export const PASSWORD_VALIDATION = {
  required: 'Password is required',
  minLength: {
    value: 6,
    message: 'Password must be at least 6 characters long',
  },
};

export const PHONE_VALIDATION = {
  required: 'Phone number is required',
  pattern: {
    value: /^[0-9]{10}$/,
    message: 'Phone number must be a 10-digit number',
  },
};

export const URL_VALIDATION = {
  pattern: {
    value: /^(https?:\/\/)?(www\.)?[-a-zA-Z0-9@:%._+~#=]{1,256}\.[a-zA-Z0-9()]{1,6}\b([-a-zA-Z0-9()@:%_+.~#?&//=]*)$/,
    message: 'Invalid URL format (e.g. https://example.com)',
  },
};

export const CGPA_VALIDATION = {
  required: 'CGPA is required',
  min: { value: 0, message: 'CGPA cannot be negative' },
  max: { value: 10, message: 'CGPA cannot exceed 10.0' },
};
