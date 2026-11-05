/* Comportamento específico deste curso. É inserido depois do runtime do coursekit,
   então `window.coursekit` e seus eventos já estão disponíveis. */
(function () {
  'use strict';

  // ---------------------------------------------------------------- "Baixar imagem"
  // Toda <img data-download="nome-do-arquivo"> ganha um botão que salva a imagem no
  // computador do aluno. Como o coursekit embute as imagens como data: URI, o botão
  // funciona offline e até com o index.html aberto direto do disco.
  var ICON = '<svg viewBox="0 0 24 24" aria-hidden="true" fill="none" stroke="currentColor" '
    + 'stroke-width="2" stroke-linecap="round" stroke-linejoin="round">'
    + '<path d="M12 3v12"/><path d="m7 10 5 5 5-5"/><path d="M4 19h16"/></svg>';

  function formatSize(bytes) {
    if (!bytes) return '';
    if (bytes >= 1024 * 1024) return (bytes / 1024 / 1024).toFixed(1).replace('.', ',') + ' MB';
    return Math.round(bytes / 1024) + ' KB';
  }

  function estimateBytes(src) {
    var i = src.indexOf('base64,');
    if (!src.startsWith('data:') || i < 0) return 0;
    var b64 = src.length - i - 7;
    var padding = src.endsWith('==') ? 2 : src.endsWith('=') ? 1 : 0;
    return Math.floor(b64 * 3 / 4) - padding;
  }

  function addDownloadButton(img) {
    var name = img.getAttribute('data-download') || 'imagem';
    var src = img.currentSrc || img.src;
    var a = document.createElement('a');
    a.className = 'button secondary';
    a.href = src;
    a.setAttribute('download', name);
    a.innerHTML = ICON + '<span>Baixar <code>' + name + '</code></span>';
    var size = formatSize(estimateBytes(src));
    if (size) a.innerHTML += '<span class="size">(' + size + ')</span>';

    // Baixar a partir de um Blob é mais robusto do que de um data: URI muito longo.
    a.addEventListener('click', function (e) {
      if (a.dataset.ready) return;
      e.preventDefault();
      var done = function (href) { a.href = href; a.dataset.ready = '1'; a.click(); };
      try {
        fetch(src).then(function (r) { return r.blob(); })
          .then(function (blob) { done(URL.createObjectURL(blob)); })
          .catch(function () { done(src); });
      } catch (err) { done(src); }
    });

    var row = document.createElement('p');
    row.className = 'download-row';
    row.appendChild(a);
    var figure = img.closest('figure');
    if (figure) {
      var caption = figure.querySelector('figcaption');
      if (caption) figure.insertBefore(row, caption); else figure.appendChild(row);
    } else {
      var host = img.closest('.phone, .preview-panel, p') || img;
      host.insertAdjacentElement('afterend', row);
    }
  }

  document.querySelectorAll('.prose img[data-download]').forEach(addDownloadButton);

  // ---------------------------------------------------------------- confetes no final
  document.addEventListener('coursekit:finish', function () {
    if (window.matchMedia('(prefers-reduced-motion: reduce)').matches) return;
    var colors = ['#3ddc84', '#0f7b4f', '#f2c66d', '#4285f4', '#ff6f91'];
    for (var i = 0; i < 90; i++) {
      var piece = document.createElement('span');
      piece.className = 'confetti';
      piece.style.left = Math.random() * 100 + 'vw';
      piece.style.background = colors[i % colors.length];
      piece.style.setProperty('--drift', (Math.random() * 30 - 15) + 'vw');
      piece.style.setProperty('--spin', (Math.random() * 900 - 450) + 'deg');
      piece.style.setProperty('--fall', (1.8 + Math.random() * 1.6) + 's');
      piece.style.animationDelay = Math.random() * 0.4 + 's';
      piece.addEventListener('animationend', function () { this.remove(); });
      document.body.appendChild(piece);
    }
  });
})();
