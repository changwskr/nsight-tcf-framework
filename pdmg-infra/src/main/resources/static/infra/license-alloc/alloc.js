const SCID='INF-720';
const $=(id)=>document.getElementById(id);
let allocRows=[];
let assetOptionsHtml='<option value="">선택</option>';

async function loadAssetOptions(){
  if(!window.InfraCombo) return;
  const items=await InfraCombo.loadMaster('assets');
  assetOptionsHtml='<option value="">선택</option>'+(items||[]).map(it=>
    `<option value="${InfraApi.escapeHtml(it.value)}">${InfraApi.escapeHtml(it.label||it.value)}</option>`
  ).join('');
}

function renderAlloc(){
  $('allocBody').innerHTML=allocRows.length?allocRows.map((r,i)=>{
    const sel=`<select data-f="assetId">${assetOptionsHtml}</select>`;
    return `<tr data-i="${i}">
    <td>${sel}</td>
    <td><input data-f="allocatedQty" type="number" step="0.01" value="${r.allocatedQty??''}"></td>
    <td><button class="btn-icon" data-action="rm" type="button">삭제</button></td></tr>`;
  }).join('')
    :'<tr><td colspan="3" class="empty">행 추가</td></tr>';
  $('allocBody').querySelectorAll('tr[data-i]').forEach((tr)=>{
    const i=Number(tr.dataset.i);
    const sel=tr.querySelector('[data-f="assetId"]');
    if(sel && window.InfraCombo) InfraCombo.set(sel, allocRows[i].assetId||'');
    else if(sel) sel.value=allocRows[i].assetId||'';
  });
}

async function search(){
  $('resultMeta').textContent='조회 중…';
  const licenseId=$('licenseId').value;
  const res=await InfraApi.postService('ifina7200S0',{licenseId},SCID);
  const dto=res.dto||{};
  if(dto.RSLT_CD&&dto.RSLT_CD!=='0000'){
    $('resultMeta').textContent=`${dto.RSLT_CD}: ${dto.RSLT_MSG||''}`;
    return;
  }
  $('summaryLine').textContent=dto.licenseId||'';
  $('sumBody').innerHTML=`<tr>
    <td>${InfraApi.escapeHtml(dto.productName||'')}</td>
    <td>${InfraApi.escapeHtml(dto.vendorName||'')}</td>
    <td>${InfraApi.escapeHtml(dto.licenseModelCd||'')}</td>
    <td>${dto.contractQty??''}</td>
    <td>${dto.allocatedSum??''}</td>
    <td>${dto.remainingQty??''}</td></tr>`;
  allocRows=(dto.allocations||[]).map(a=>({assetId:a.assetId||'', allocatedQty:a.allocatedQty??0}));
  renderAlloc();
  $('resultMeta').textContent=`HTTP ${res.httpStatus} · ${res.elapsedMs}ms · ifina7200S0`;
}

function collectRows(){
  const out=[];
  $('allocBody').querySelectorAll('tr[data-i]').forEach(tr=>{
    const assetId=(tr.querySelector('[data-f="assetId"]').value||'').trim();
    const q=tr.querySelector('[data-f="allocatedQty"]').value;
    if(!assetId) return;
    out.push({assetId, allocatedQty:q===''?0:Number(q)});
  });
  return out;
}

async function save(){
  const licenseId=$('licenseId').value;
  const allocations=collectRows();
  const res=await InfraApi.postService('ifina7200U0',{licenseId, allocations},SCID);
  const dto=res.dto||{};
  if(dto.RSLT_CD&&dto.RSLT_CD!=='0000'){alert(`${dto.RSLT_CD}: ${dto.RSLT_MSG||''}`);return;}
  await search();
}

$('searchBtn').onclick=()=>search().catch(console.error);
$('saveBtn').onclick=()=>save().catch(console.error);
$('licenseId').onchange=()=>search().catch(console.error);
$('addRowBtn').onclick=()=>{allocRows.push({assetId:'', allocatedQty:0}); renderAlloc();};
$('allocBody').onclick=(ev)=>{
  const btn=ev.target.closest('[data-action="rm"]'); if(!btn)return;
  const i=Number(btn.closest('tr').dataset.i);
  allocRows=collectRows();
  allocRows.splice(i,1);
  renderAlloc();
};
(async()=>{
  if(window.InfraCombo){ await InfraCombo.boot(); await loadAssetOptions(); }
  await search();
})().catch(console.error);
