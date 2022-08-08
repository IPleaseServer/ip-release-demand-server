package site.iplease.irdserver.infra.account.data.type

enum class DepartmentType(
    vararg clazz: Int
) {
    SMART_IOT(3, 4), SOFTWARE_DEVELOP(1, 2);

    val classes = clazz.toSet()
}