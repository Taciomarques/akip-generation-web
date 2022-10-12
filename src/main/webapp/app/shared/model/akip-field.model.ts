export interface IAkipField {
  fieldName?: string | null;
  fieldEnum?: string | null;
  fieldType?: string | null;
  fieldValues?: string | null;
  fieldReadOnly?: boolean | null;
  fieldValidateRules?: string[] | null;
  fieldValidateRulesMin?: number | null;
  fieldValidateRulesMax?: number | null;
  fieldValidateRulesMaxlength?: number | null;
  fieldValidateRulesMinlength?: number | null;
  fieldValidateRulesPattern?: string | null;
  fieldTypeBlobContent?: string | null;
}

export class AkipField implements IAkipField {
  constructor(
    public fieldName?: string | null,
    public fieldEnum?: string | null,
    public fieldType?: string | null,
    public fieldValues?: string | null,
    public fieldReadOnly?: boolean | null,
    public fieldValidateRules?: string[] | null,
    public fieldValidateRulesMin?: number | null,
    public fieldValidateRulesMax?: number | null,
    public fieldValidateRulesMaxlength?: number | null,
    public fieldValidateRulesMinlength?: number | null,
    public fieldValidateRulesPattern?: string | null,
    public fieldTypeBlobContent?: string | null
  ) {}
}
