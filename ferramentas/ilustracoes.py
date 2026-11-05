#!/usr/bin/env python3
"""Ilustrações do curso: cada desenho é descrito uma vez e sai em dois formatos.

  assets/pratos/<nome>.svg           usado nas páginas do curso
  assets/pratos/android/<nome>.xml   VectorDrawable, copiado para res/drawable dos apps

Uso:
  python3 ferramentas/ilustracoes.py           gera os arquivos em assets/pratos/
  python3 ferramentas/ilustracoes.py --apps    também atualiza as cópias que já existem em
                                           exercicios/ e solucoes/ (res/drawable/<nome>.xml)

Os dois formatos usam só caminhos (path) com os mesmos dados, por isso ficam idênticos.
Tela de 200 x 200; cada desenho fica dentro de um círculo de cor suave.
"""
from __future__ import annotations

import sys
from pathlib import Path

RAIZ = Path(__file__).resolve().parent.parent
SAIDA = RAIZ / "assets" / "pratos"


# --- formas -------------------------------------------------------------------
def n(v: float) -> str:
    return f"{v:.2f}".rstrip("0").rstrip(".")


def circulo(cx, cy, r):
    return elipse(cx, cy, r, r)


def elipse(cx, cy, rx, ry):
    return (f"M{n(cx - rx)},{n(cy)} a{n(rx)},{n(ry)} 0 1,0 {n(2 * rx)},0 "
            f"a{n(rx)},{n(ry)} 0 1,0 {n(-2 * rx)},0 Z")


def retangulo(x, y, w, h, r=0):
    if r == 0:
        return f"M{n(x)},{n(y)} h{n(w)} v{n(h)} h{n(-w)} Z"
    return (f"M{n(x + r)},{n(y)} h{n(w - 2 * r)} a{n(r)},{n(r)} 0 0,1 {n(r)},{n(r)} "
            f"v{n(h - 2 * r)} a{n(r)},{n(r)} 0 0,1 {n(-r)},{n(r)} h{n(-(w - 2 * r))} "
            f"a{n(r)},{n(r)} 0 0,1 {n(-r)},{n(-r)} v{n(-(h - 2 * r))} "
            f"a{n(r)},{n(r)} 0 0,1 {n(r)},{n(-r)} Z")


def linha(x1, y1, x2, y2):
    return f"M{n(x1)},{n(y1)} L{n(x2)},{n(y2)}"


def cheio(d, cor, alfa=1.0):
    return {"d": d, "fill": cor, "alfa": alfa}


def traco(d, cor, largura, alfa=1.0):
    return {"d": d, "stroke": cor, "largura": largura, "alfa": alfa}


def fundo(cor):
    return cheio(circulo(100, 100, 96), cor)


def sombra(cx, cy, rx, ry=7):
    return cheio(elipse(cx, cy, rx, ry), "#000000", 0.13)


# --- cores --------------------------------------------------------------------
CHOCOLATE = "#5A3220"
CHOCOLATE_ESCURO = "#3F2114"
CHOCOLATE_CLARO = "#7A4A32"
BRANCO = "#FFFFFF"


def bola_de_chocolate(cx, cy, r):
    return [
        cheio(circulo(cx, cy, r), CHOCOLATE_ESCURO),
        cheio(circulo(cx - r * 0.06, cy - r * 0.08, r * 0.92), CHOCOLATE),
        cheio(elipse(cx - r * 0.36, cy - r * 0.42, r * 0.26, r * 0.15), BRANCO, 0.2),
    ]


def granulado(pontos):
    cores = ["#8A5A3C", "#2E170D", "#B07A52"]
    formas = []
    for i, (x, y, dx, dy) in enumerate(pontos):
        formas.append(traco(linha(x, y, x + dx, y + dy), cores[i % 3], 4.2))
    return formas


# --- pratos -------------------------------------------------------------------
def brigadeiro():
    f = [fundo("#F9D9C5"), sombra(100, 163, 50)]
    f += bola_de_chocolate(100, 92, 46)
    f += granulado([
        (72, 70, 7, -4), (90, 58, 8, 2), (112, 60, 6, 5), (128, 74, 7, -3),
        (62, 90, 6, 6), (82, 84, 8, -2), (102, 78, 2, 8), (120, 90, 8, 1),
        (138, 96, -4, 7), (70, 108, 8, 2), (92, 102, 6, -6), (112, 106, 7, 4),
        (130, 112, -7, 3), (98, 66, -7, 3),
    ])
    # forminha de papel, na frente da bola
    f.append(cheio("M48,116 Q100,136 152,116 L139,158 Q100,168 61,158 Z", "#FFF4E2"))
    f.append(cheio("M48,116 Q100,136 152,116 L150,123 Q100,143 50,123 Z", "#EAD3B0"))
    for x_topo, x_base in [(66, 73), (83, 87), (100, 100), (117, 113), (134, 127)]:
        f.append(traco(linha(x_topo, 131, x_base, 159), "#E3C9A2", 2.4))
    return f


def pao_de_queijo():
    f = [fundo("#D3EBD9"), sombra(100, 162, 62)]

    def pao(cx, cy, r):
        return [
            cheio(circulo(cx, cy, r), "#D9A441"),
            cheio(circulo(cx - 2, cy - 3, r - 3), "#F1C562"),
            cheio(elipse(cx - r * 0.3, cy - r * 0.42, r * 0.34, r * 0.2), "#FBE3A0"),
            cheio(elipse(cx + r * 0.3, cy + r * 0.12, r * 0.14, r * 0.09), "#DDA744"),
            cheio(elipse(cx - r * 0.12, cy + r * 0.42, r * 0.12, r * 0.07), "#DDA744"),
            cheio(elipse(cx + r * 0.42, cy - r * 0.32, r * 0.09, r * 0.06), "#DDA744"),
        ]

    f += pao(100, 78, 33)
    f += pao(70, 124, 35)
    f += pao(132, 124, 35)
    return f


def bolo_de_cenoura():
    f = [fundo("#D7E5F8"), sombra(100, 160, 66)]
    f.append(cheio(elipse(100, 154, 70, 10), BRANCO))
    # massa
    f.append(cheio(retangulo(52, 82, 96, 68, 8), "#F29A36"))
    f.append(cheio(retangulo(52, 136, 96, 14, 7), "#DE8324"))
    for x, y, r in [(70, 108, 3.2), (92, 122, 2.6), (116, 106, 3), (132, 126, 2.6),
                    (104, 132, 2.2), (78, 130, 2.4), (126, 112, 2)]:
        f.append(cheio(circulo(x, y, r), "#F9BE6C"))
    # cobertura de chocolate, com gotas escorrendo
    f.append(cheio(
        "M46,84 Q46,62 68,62 L132,62 Q154,62 154,84 L154,92 Q154,100 147,100 "
        "Q140,100 140,92 Q140,88 134,88 Q128,88 128,96 L128,108 Q128,116 121,116 "
        "Q114,116 114,108 L114,94 Q114,88 107,88 Q100,88 100,94 L100,98 Q100,105 93,105 "
        "Q86,105 86,98 L86,94 Q86,88 79,88 Q72,88 72,96 L72,112 Q72,120 65,120 "
        "Q58,120 58,112 L58,96 Q58,92 52,92 Q46,92 46,84 Z", CHOCOLATE))
    f.append(traco("M64,72 Q100,66 136,72", CHOCOLATE_CLARO, 5))
    return f


def pudim():
    f = [fundo("#F6DCE8"), sombra(100, 166, 72)]
    f.append(cheio(elipse(100, 156, 80, 12), BRANCO))
    f.append(cheio(elipse(100, 153, 66, 9), "#C2681E"))
    # corpo
    f.append(cheio("M50,152 L68,80 Q70,68 82,68 L118,68 Q130,68 132,80 L150,152 "
                   "Q100,164 50,152 Z", "#F3C263"))
    f.append(cheio("M114,68 L118,68 Q130,68 132,80 L150,152 Q140,155 128,156 Z", "#E2AA48"))
    # calda
    f.append(cheio("M65,90 L68,80 Q70,66 82,66 L118,66 Q130,66 132,80 L136,98 "
                   "Q136,107 129,107 Q122,107 121,98 Q120,89 113,89 Q106,89 106,98 "
                   "L106,106 Q106,114 99,114 Q92,114 92,106 L92,96 Q92,89 85,89 "
                   "Q78,89 77,97 Q76,105 70,105 Q63,105 65,90 Z", "#A4511A"))
    f.append(traco("M82,74 Q100,70 118,74", "#C97A3A", 3.6))
    return f


def coxinha():
    f = [fundo("#E0ECC0"), sombra(100, 166, 50)]
    corpo = ("M100,36 C110,60 150,94 150,124 C150,150 128,164 100,164 "
             "C72,164 50,150 50,124 C50,94 90,60 100,36 Z")
    f.append(cheio(corpo, "#C57B2C"))
    f.append(cheio("M99,44 C107,66 142,96 142,122 C142,144 123,156 99,156 "
                   "C76,156 57,144 57,122 C57,96 91,66 99,44 Z", "#DC9440"))
    f.append(cheio(elipse(82, 104, 9, 20), "#EFB465", 0.75))
    for x, y, r, cor in [
        (104, 76, 2.2, "#B36A22"), (88, 126, 2.4, "#B36A22"), (118, 104, 2.6, "#B36A22"),
        (126, 132, 2.2, "#B36A22"), (100, 144, 2.4, "#B36A22"), (72, 138, 2, "#B36A22"),
        (110, 124, 2, "#F4C784"), (96, 100, 2, "#F4C784"), (130, 116, 1.8, "#F4C784"),
        (84, 146, 1.8, "#F4C784"), (114, 146, 2, "#F4C784"), (108, 92, 1.8, "#F4C784"),
    ]:
        f.append(cheio(circulo(x, y, r), cor))
    return f


def acai():
    f = [fundo("#FFE7B0"), sombra(100, 166, 56)]
    # tigela
    f.append(cheio(retangulo(80, 150, 40, 12, 4), "#D8CBB8"))
    f.append(cheio("M38,96 L162,96 A62,60 0 0,1 38,96 Z", "#F6EFE4"))
    f.append(cheio("M122,152 A62,60 0 0,0 162,96 L148,96 A50,54 0 0,1 112,154 Z", "#E3D7C5"))
    # açaí
    f.append(cheio(elipse(100, 96, 62, 17), "#F6EFE4"))
    f.append(cheio(elipse(100, 96, 56, 13.5), "#4A1C5C"))
    f.append(cheio(elipse(100, 94, 50, 10), "#63287A"))
    # banana
    for x, y in [(74, 92), (96, 87), (88, 100)]:
        f.append(cheio(elipse(x, y, 11, 5.5), "#F0CC62"))
        f.append(cheio(elipse(x, y - 0.6, 9.4, 4.3), "#FBE591"))
        f.append(cheio(elipse(x, y - 0.6, 2.2, 1.1), "#E2BD52"))
    # morango
    for x, y in [(122, 90), (136, 97)]:
        f.append(cheio(elipse(x, y, 8, 5), "#D7373F"))
        f.append(cheio(elipse(x - 1, y - 0.8, 4.4, 2.4), "#F0848A"))
    # granola
    for x, y in [(110, 100), (118, 103), (104, 104), (126, 101), (62, 98), (114, 95)]:
        f.append(cheio(elipse(x, y, 2.6, 1.6), "#D09A58"))
    return f


def prato_vazio():
    f = [fundo("#E3E7EC")]
    f.append(cheio(circulo(100, 102, 56), "#CBD2DA"))
    f.append(cheio(circulo(100, 98, 56), BRANCO))
    f.append(traco(circulo(100, 98, 36), "#D5DAE1", 3))
    for x, y in [(92, 92), (106, 102), (98, 108), (112, 90)]:
        f.append(cheio(circulo(x, y, 1.8), "#C7A27A"))
    # garfo
    f.append(traco(linha(26, 104, 26, 150), "#8E99A6", 5))
    for x in (19, 26, 33):
        f.append(traco(linha(x, 62, x, 84), "#8E99A6", 3.4))
    f.append(traco("M19,84 Q19,100 26,104 Q33,100 33,84", "#8E99A6", 3.4))
    # faca
    f.append(traco(linha(174, 108, 174, 150), "#8E99A6", 5))
    f.append(cheio("M171,108 L171,70 Q171,58 178,58 Q182,82 178,108 Z", "#8E99A6"))
    return f


# --- passos do brigadeiro -------------------------------------------------------
def panela(com_fogo):
    f = []
    if com_fogo:
        for x in (72, 100, 128):
            f.append(cheio(f"M{x},152 Q{x - 11},164 {x},178 Q{x + 11},164 {x},152 Z", "#F28C28"))
            f.append(cheio(f"M{x},162 Q{x - 5},169 {x},177 Q{x + 5},169 {x},162 Z", "#FFD166"))
    f.append(cheio(retangulo(148, 112, 40, 10, 5), "#3A4048"))
    f.append(cheio("M50,104 L150,104 L146,146 Q145,156 135,156 L65,156 Q55,156 54,146 Z",
                   "#73808F"))
    f.append(cheio("M126,104 L150,104 L146,146 Q145,156 135,156 L120,156 Z", "#5F6B79"))
    f.append(cheio(retangulo(44, 98, 112, 10, 5), "#98A5B3"))
    return f


def panela_ingredientes():
    f = [fundo("#F9D9C5")]
    f += panela(com_fogo=False)
    # lata de leite condensado
    f.append(cheio(retangulo(52, 34, 38, 50, 5), "#F4F6F8"))
    f.append(cheio(retangulo(52, 48, 38, 22, 0), "#3D7FC1"))
    f.append(cheio(elipse(71, 59, 9, 6), BRANCO))
    f.append(cheio(elipse(71, 35, 19, 4.5), "#C9D1D9"))
    # barra de chocolate
    f.append(cheio(retangulo(108, 38, 42, 46, 5), CHOCOLATE))
    f.append(traco(linha(122, 40, 122, 82), CHOCOLATE_ESCURO, 2.4))
    f.append(traco(linha(136, 40, 136, 82), CHOCOLATE_ESCURO, 2.4))
    f.append(traco(linha(110, 53, 148, 53), CHOCOLATE_ESCURO, 2.4))
    f.append(traco(linha(110, 68, 148, 68), CHOCOLATE_ESCURO, 2.4))
    return f


def panela_mexendo():
    f = [fundo("#FBE2B4")]
    f += panela(com_fogo=True)
    f.append(cheio(elipse(100, 103, 50, 6), CHOCOLATE))
    # colher de pau
    f.append(traco(linha(134, 36, 108, 100), "#C9945A", 9))
    f.append(traco(linha(134, 36, 108, 100), "#DDB07A", 3.4))
    # vapor
    for x in (62, 84):
        f.append(traco(f"M{x},84 q-7,-9 0,-18 q7,-9 0,-18 q-7,-9 0,-18", BRANCO, 4.4, 0.85))
    return f


def bolinhas():
    f = [fundo("#D7E5F8"), sombra(100, 160, 78, 9)]
    f.append(cheio(elipse(100, 132, 84, 26), "#E9EDF2"))
    f.append(cheio(elipse(100, 128, 84, 26), BRANCO))
    f.append(traco(elipse(100, 128, 62, 17), "#DFE4EA", 2.4))
    for cx, cy in [(64, 104), (100, 96), (136, 104), (82, 124), (118, 124)]:
        f += bola_de_chocolate(cx, cy, 19)
    return f


DESENHOS = {
    "brigadeiro": brigadeiro,
    "pao_de_queijo": pao_de_queijo,
    "bolo_de_cenoura": bolo_de_cenoura,
    "pudim": pudim,
    "coxinha": coxinha,
    "acai": acai,
    "prato_vazio": prato_vazio,
    "panela_ingredientes": panela_ingredientes,
    "panela_mexendo": panela_mexendo,
    "bolinhas": bolinhas,
}


# --- saída --------------------------------------------------------------------
def para_svg(formas) -> str:
    linhas = ['<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 200 200">']
    for f in formas:
        if "fill" in f:
            alfa = f' fill-opacity="{n(f["alfa"])}"' if f["alfa"] < 1 else ""
            linhas.append(f'  <path d="{f["d"]}" fill="{f["fill"]}"{alfa}/>')
        else:
            alfa = f' stroke-opacity="{n(f["alfa"])}"' if f["alfa"] < 1 else ""
            linhas.append(f'  <path d="{f["d"]}" fill="none" stroke="{f["stroke"]}" '
                          f'stroke-width="{n(f["largura"])}" stroke-linecap="round" '
                          f'stroke-linejoin="round"{alfa}/>')
    linhas.append("</svg>")
    return "\n".join(linhas) + "\n"


def caminhos_android(formas, recuo="    ") -> list[str]:
    linhas = []
    for f in formas:
        linhas.append(f"{recuo}<path")
        linhas.append(f'{recuo}    android:pathData="{f["d"]}"')
        if "fill" in f:
            if f["alfa"] < 1:
                linhas.append(f'{recuo}    android:fillAlpha="{n(f["alfa"])}"')
            linhas.append(f'{recuo}    android:fillColor="{f["fill"]}" />')
        else:
            if f["alfa"] < 1:
                linhas.append(f'{recuo}    android:strokeAlpha="{n(f["alfa"])}"')
            linhas.append(f'{recuo}    android:strokeColor="{f["stroke"]}"')
            linhas.append(f'{recuo}    android:strokeLineCap="round"')
            linhas.append(f'{recuo}    android:strokeLineJoin="round"')
            linhas.append(f'{recuo}    android:strokeWidth="{n(f["largura"])}" />')
    return linhas


def para_android(formas) -> str:
    linhas = [
        '<vector xmlns:android="http://schemas.android.com/apk/res/android"',
        '    android:width="200dp"',
        '    android:height="200dp"',
        '    android:viewportWidth="200"',
        '    android:viewportHeight="200">',
    ]
    linhas += caminhos_android(formas)
    linhas.append("</vector>")
    return "\n".join(linhas) + "\n"


def icone_do_app() -> str:
    """Primeiro plano do ícone adaptável (108 x 108): o brigadeiro, sem o círculo de fundo."""
    formas = brigadeiro()[2:]
    linhas = [
        '<vector xmlns:android="http://schemas.android.com/apk/res/android"',
        '    android:width="108dp"',
        '    android:height="108dp"',
        '    android:viewportWidth="108"',
        '    android:viewportHeight="108">',
        "    <group",
        '        android:scaleX="0.44"',
        '        android:scaleY="0.44"',
        '        android:translateX="10"',
        '        android:translateY="7.6">',
    ]
    linhas += caminhos_android(formas, recuo="        ")
    linhas += ["    </group>", "</vector>"]
    return "\n".join(linhas) + "\n"


def main() -> None:
    (SAIDA / "android").mkdir(parents=True, exist_ok=True)
    gerados = {}
    for nome, desenho in DESENHOS.items():
        formas = desenho()
        (SAIDA / f"{nome}.svg").write_text(para_svg(formas), encoding="utf-8")
        gerados[f"{nome}.xml"] = para_android(formas)
    gerados["ic_launcher_foreground.xml"] = icone_do_app()
    for arquivo, conteudo in gerados.items():
        (SAIDA / "android" / arquivo).write_text(conteudo, encoding="utf-8")
    print(f"{len(DESENHOS)} ilustrações em {SAIDA.relative_to(RAIZ)}/")

    if "--apps" in sys.argv:
        atualizados = 0
        for pasta in ("exercicios", "solucoes"):
            for destino in (RAIZ / pasta).glob("*/app/src/main/res/drawable/*.xml"):
                if destino.name in gerados:
                    destino.write_text(gerados[destino.name], encoding="utf-8")
                    atualizados += 1
        print(f"{atualizados} cópias atualizadas nos apps")


if __name__ == "__main__":
    main()
