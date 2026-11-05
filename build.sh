#!/usr/bin/env sh
# Gera este curso com o coursekit, a ferramenta que fica na pasta ao lado (../coursekit).
# Ela é usada por referência: uma atualização do coursekit vale aqui já no próximo build.
#
#   ./build.sh                gera dist/index.html
#   ./build.sh dev            pré-visualização em http://127.0.0.1:8000, refeita e recarregada ao salvar
#   ./build.sh --strict       falha se houver avisos (imagem ausente, link quebrado…), como no CI
#   COURSEKIT=/caminho/do/coursekit ./build.sh     usa um coursekit que está em outro lugar
set -e
cd "$(dirname "$0")"

COURSEKIT="${COURSEKIT:-../coursekit}"
if [ ! -f "$COURSEKIT/bin/coursekit" ]; then
  echo "coursekit não encontrado em '$COURSEKIT' (a partir de $(pwd))." >&2
  echo "Clone-o ao lado deste curso ou informe COURSEKIT=/caminho/do/coursekit." >&2
  exit 1
fi

if [ "$1" = "dev" ]; then
  shift
  exec sh "$COURSEKIT/bin/coursekit" dev . "$@"
fi
exec sh "$COURSEKIT/bin/coursekit" build . "$@"
