/* tslint:disable */
/* eslint-disable */
// Generated using typescript-generator version 3.1.1185 on 2023-03-09 22:22:36.

export interface UserForm extends AbstractRestEntity {
  "@type": "User";
  nickName: string;
  firstName: string;
  lastName: string;
  birthDate: string;
  password: string;
  picture: MediaType;
  background: MediaType;
  emails: EmailForm[];
  enabled: boolean;
  language: LanguageForm;
  roles: RoleForm[];
}

export interface ResponseOk extends AbstractSchemaEntity {
  ok: boolean;
}

export interface AbstractRestEntity extends RestEntity, LinksContainer {
  created: string;
  modified: string;
  links: { [index: string]: Link };
}

export interface LoginResponse extends AbstractSchemaEntity {
  token: string;
}

export interface LogoutForm extends LinksContainer {
  $schema: JsonObject;
}

export interface RegisterForm extends LinksContainer {
  nickname: string;
  email: string;
  password: string;
  $schema: JsonObject;
}

export interface MessageForm {
  text: string;
  from: string;
  to: string[];
}

export interface MapBoxAddress extends AbstractSchemaEntity {
  name: string;
  description: string;
  point: MapBoxPoint;
}

export interface FeatureCollection {
  query: string[];
  features: Feature[];
}

export interface Locale extends Cloneable, Serializable {
}

export interface NotificationActiveResponse {
  size: number;
}

export interface SystemForm extends AbstractSchemaEntity {
  properties: { [index: string]: any };
}

export interface MovieSearchResponse {
  page: number;
  results: MovieSearchResult[];
  total_results: number;
  total_pages: number;
}

export interface Form<E> extends AbstractSchemaEntity {
  form: E;
}

export interface UserSelect extends IdentitySelect {
  "@type": "User";
  nickName: string;
  firstName: string;
  lastName: string;
  birthDate: string;
  language: LanguageForm;
}

export interface Link {
  url: string;
  method: string;
  rel: string;
  description: string;
  type: LinkType;
}

export interface MediaType extends AbstractRestEntity {
  name: string;
  lastModified: string;
  data: string;
  thumbnail: Thumbnail;
}

export interface EmailForm {
  value: string;
  confirmed: boolean;
}

export interface LanguageForm extends AbstractRestEntity {
  "@type": "Language";
  locale: Locale;
  name: string;
}

export interface RoleForm extends AbstractRestEntity {
  "@type": "Role";
  name: string;
  description: string;
}

export interface JsonObject extends JsonNode {
  type: "object";
  properties: { [index: string]: JsonNodeUnion };
  additionalProperties: boolean;
  unevaluatedProperties: boolean;
  required: string[];
  minProperties: number;
  maxProperties: number;
}

export interface AbstractSchemaEntity {
  $schema: JsonObject;
}

export interface RestEntity {
  id: string;
}

export interface LinksContainer {
}

export interface CategoryForm extends AbstractRestEntity {
  "@type": "Category";
  name: string;
  description: string;
  owner: UserSelect;
}

export interface UserConnectionForm extends AbstractRestEntity {
  "@type": "UserConnection";
  from: UserSelect;
  category: CategoryForm;
  accepted: boolean;
  to: UserSelect;
}

export interface LoginForm {
  "@type": "User";
  email: string;
  password: string;
}

export interface CommunityForm extends AbstractRestEntity {
  "@type": "Community";
  name: string;
  description: string;
}

export interface CommunityConnectionForm extends AbstractRestEntity {
  "@type": "CommunityConnection";
  from: UserSelect;
  status: Status;
  role: RoleForm;
  to: CommunityForm;
}

export interface SecuredForm<E> extends AbstractSchemaEntity {
  form: E;
}

export interface AddressSelect extends AbstractRestEntity {
  "@type": "Address";
  name: MapBoxAddress;
}

export interface AddressForm extends AbstractRestEntity {
  "@type": "Address";
  owner: UserSelect;
  description: string;
  street: string;
  zipCode: string;
  state: string;
  country: string;
  x: number;
  y: number;
}

export interface MapBoxPoint {
  x: number;
  y: number;
}

export interface Table<R> extends AbstractSchemaEntity {
  rows: R[];
  size: number;
}

export interface Feature {
  id: string;
  relevance: number;
  properties: { [index: string]: string };
  text: string;
  bbox: number[];
  center: number[];
  geometry: Point;
  context: Context[];
  place_type: string[];
  place_name: string;
}

export interface EventForm extends AbstractRestEntity {
  "@type": "Resume";
  owner: UserSelect;
  site: SiteForm;
  start: string;
  end: string;
}

export interface PageForm extends AbstractLikeableRestEntity {
  title: string;
  editor: Editor;
  language: LanguageForm;
  modifier: UserSelect;
}

export interface AnswerForm extends AbstractLikeableRestEntity {
  "@type": "Answer";
  editor: Editor;
  question: QuestionForm;
  owner: UserSelect;
}

export interface QuestionForm extends AbstractLikeableRestEntity {
  page: PageForm;
  topic: string;
  editor: Editor;
  owner: UserSelect;
}

export interface SiteConnectionForm extends AbstractRestEntity {
  from: UserSelect;
  to: SiteSelect;
}

export interface SiteForm extends AbstractRestEntity {
  "@type": "Site";
  name: AlternativeForm;
  homepage: AlternativeForm;
  phone: AlternativeForm;
  address: MapBoxAddress;
  picture: MediaType;
}

export interface CommentForm extends AbstractLikeableRestEntity {
  "@type": "Comment";
  text: string;
  post: string;
  parent: string;
  owner: UserSelect;
}

export interface AbstractPostForm extends AbstractLikeableRestEntity {
  "@type": "ImagePost" | "VideoPost" | "TextPost";
  editor: Editor;
  owner: UserSelect;
  source: IdentitySelect;
  comments: CommentForm[];
}

export interface Cloneable {
}

export interface Serializable {
}

export interface TemplateForm extends AbstractRestEntity {
  "@type": "Template";
  name: string;
  language: LanguageForm;
  content: Editor;
}

export interface MovieSearchResult {
  adult: boolean;
  overview: string;
  id: number;
  title: string;
  popularity: number;
  video: boolean;
  poster_path: string;
  release_date: string;
  genre_ids: number[];
  original_title: string;
  original_language: string;
  backdrop_path: string;
  vote_count: number;
  vote_average: number;
}

export interface IdentitySelect extends AbstractRestEntity {
  name: string;
  picture: MediaType;
}

export interface Thumbnail extends AbstractRestEntity {
  name: string;
  lastModified: string;
  data: string;
}

export interface Validator {
  "@type": "decimalMaxValidator" | "decimalMinValidator" | "digitsValidator" | "emailValidator" | "futureOrPresentValidator" | "futureValidator" | "maxValidator" | "minValidator" | "negativeOrZeroValidator" | "negativeValidator" | "notBlankValidator" | "notNullValidator" | "pastOrPresentValidator" | "pastValidator" | "patternValidator" | "positiveOrZeroValidator" | "sizeValidator";
}

export interface JsonNode {
  type: "object" | "string" | "array" | "boolean" | "image" | "enum" | "integer" | "double" | "float";
  title: string;
  description: string;
  widget: Widget;
  naming: boolean;
  id: boolean;
  readOnly: boolean;
  dirty: boolean;
  visible: boolean;
  visibility: boolean;
  links: { [index: string]: Link };
  validators: { [index: string]: ValidatorUnion };
}

export interface Point {
  coordinates: number[];
}

export interface Context {
  id: string;
  wikidata: string;
  text: string;
  short_code: string;
}

export interface Editor {
  html: string;
  text: string;
}

export interface AbstractLikeableRestEntity extends AbstractRestEntity {
  views: number;
  likes: boolean;
}

export interface SiteSelect extends AbstractRestEntity {
  "@type": "Site";
  name: string;
  homepage: string;
  phone: string;
  address: MapBoxAddress;
  picture: MediaType;
}

export interface AlternativeForm extends AbstractRestEntity {
  "@type": "Alternative";
  value: string;
  property: string;
  entity: string;
  owner: UserSelect;
  count: number;
}

export interface ImagePostForm extends AbstractPostForm {
  "@type": "ImagePost";
  image: MediaType;
}

export interface VideoPostForm extends AbstractPostForm {
  "@type": "VideoPost";
  video: MediaType;
}

export interface TextPostForm extends AbstractPostForm {
  "@type": "TextPost";
}

export interface DecimalMaxValidator extends Validator {
  "@type": "decimalMaxValidator";
  value: string;
}

export interface DecimalMinValidator extends Validator {
  "@type": "decimalMinValidator";
  value: string;
}

export interface DigitsValidator extends Validator {
  "@type": "digitsValidator";
  integer: number;
  fraction: number;
}

export interface EmailValidator extends Validator {
  "@type": "emailValidator";
}

export interface FutureOrPresentValidator extends Validator {
  "@type": "futureOrPresentValidator";
}

export interface FutureValidator extends Validator {
  "@type": "futureValidator";
}

export interface MaxValidator extends Validator {
  "@type": "maxValidator";
  value: number;
}

export interface MinValidator extends Validator {
  "@type": "minValidator";
  value: number;
}

export interface NegativeOrZeroValidator extends Validator {
  "@type": "negativeOrZeroValidator";
}

export interface NegativeValidator extends Validator {
  "@type": "negativeValidator";
}

export interface NotBlankValidator extends Validator {
  "@type": "notBlankValidator";
}

export interface NotNullValidator extends Validator {
  "@type": "notNullValidator";
}

export interface PastOrPresentValidator extends Validator {
  "@type": "pastOrPresentValidator";
}

export interface PastValidator extends Validator {
  "@type": "pastValidator";
}

export interface PatternValidator extends Validator {
  "@type": "patternValidator";
  regexp: string;
}

export interface PositiveOrZeroValidator extends Validator {
  "@type": "positiveOrZeroValidator";
}

export interface SizeValidator extends Validator {
  "@type": "sizeValidator";
  min: number;
  max: number;
}

export interface JsonString extends JsonNode {
  type: "string";
  minLength: number;
  maxLength: number;
  pattern: string;
  format: Format;
  contentMediaType: string;
  contentEncoding: string;
}

export interface JsonInteger extends JsonNumeric {
  type: "integer";
}

export interface JsonDouble extends JsonNumeric {
  type: "double";
}

export interface JsonFloat extends JsonNumeric {
  type: "float";
}

export interface JsonArray extends JsonNode {
  type: "array";
  items: JsonNodeUnion;
  minContains: number;
  maxContains: number;
  uniqueItems: boolean;
}

export interface JsonBoolean extends JsonNode {
  type: "boolean";
}

export interface JsonNull {
}

export interface JsonImage extends JsonNode {
  type: "image";
}

export interface JsonEnum extends JsonNode {
  type: "enum";
  enum: string[];
}

export interface JsonNumeric extends JsonNode {
  type: "integer" | "double" | "float";
  multiplyOf: number;
  maximum: number;
  minimum: number;
}

export type LinkType = "table" | "form";

export type Widget = "no-widget" | "checkbox" | "color" | "date" | "datetime-local" | "email" | "file" | "hidden" | "image" | "month" | "number" | "password" | "radio" | "range" | "reset" | "search" | "submit" | "tel" | "text" | "time" | "url" | "week" | "select" | "form" | "table" | "repeat" | "lazy-multi-select" | "lazy-select" | "lazy-select-name" | "textarea" | "editor" | "like" | "json";

export type Status = "PENDING" | "OK" | "DENIED";

export type Format = "noop" | "date-time" | "time" | "date" | "duration" | "email" | "idn-email" | "hostname" | "idn-hostname" | "ip4" | "ip6" | "uuid" | "uri" | "uri-reference" | "iri" | "iri-reference" | "uri-template" | "json-pointer" | "relative-json-pointer" | "regex" | "big-integer" | "big-decimal";

export type AbstractPostFormUnion = ImagePostForm | VideoPostForm | TextPostForm;

export type ValidatorUnion = DecimalMaxValidator | DecimalMinValidator | DigitsValidator | EmailValidator | FutureOrPresentValidator | FutureValidator | MaxValidator | MinValidator | NegativeOrZeroValidator | NegativeValidator | NotBlankValidator | NotNullValidator | PastOrPresentValidator | PastValidator | PatternValidator | PositiveOrZeroValidator | SizeValidator;

export type JsonNodeUnion = JsonString | JsonInteger | JsonDouble | JsonFloat | JsonObject | JsonArray | JsonBoolean | JsonNull | JsonImage | JsonEnum;
