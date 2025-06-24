FROM openjdk:21-alpine
LABEL authors="mrminnthitoo"

ENTRYPOINT ["top", "-b"]