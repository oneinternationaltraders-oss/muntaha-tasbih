export function patchHtml(h){
 const m=h.match(/<div class="myprayers-actions">[\s\S]*?<\/div>/);
 if(!m)throw Error("folder menu not found");
 h=h.replace(m[0],'<div class="myprayers-actions"><button type="button" class="active" aria-pressed="true">قضا نمازیں</button></div>');
 const r=[
 ['.myprayers-actions{display:grid;grid-template-columns:repeat(4,minmax(0,1fr));gap:5px;margin:8px 0 12px}','.myprayers-actions{display:grid;grid-template-columns:1fr;gap:5px;margin:8px 0 12px}'],
 ['.myprayers-actions{grid-template-columns:repeat(4,minmax(0,1fr))}','.myprayers-actions{grid-template-columns:1fr}'],
 ['$("settingsView").classList.toggle("hidden",!settings)}',
  '$("settingsView").classList.toggle("hidden",!settings);if(myprayers){const v=$("myPrayersView"),p=$("prayerCalendarPanel");if(v&&p){p.classList.remove("hidden");v.insertBefore(p,v.firstChild);document.querySelectorAll("#myPrayersView > .myprayers-hero,#myPrayersView > .myprayers-section-title,#myPrayersView > .qaza-grid").forEach(x=>x.classList.remove("hidden"))}}}']
 ];
 for(const [a,b] of r){if(!h.includes(a))throw Error("patch target not found");h=h.replace(a,b)}

 const initOld = "const startInput=$pc('pcStartDate'); let startSaved=!!localStorage.getItem(START),start=localStorage.getItem(START)||iso(today()); startInput.value=start;startInput.disabled=startSaved;if(startSaved){$pc('pcSaveStart').disabled=true;$pc('pcSaveStart').textContent='محفوظ ہوگیا ✓'}";
 const initNew = "const startInput���q�^