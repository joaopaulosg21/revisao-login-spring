package revisao.api.crudrevisao.common;

public record CommonResponse<T>(T object, String message) {
}
