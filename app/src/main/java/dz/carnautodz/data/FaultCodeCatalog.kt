package dz.carnautodz.data

/**
 * Offline OBD-II fault-code reference.
 *
 * Codes themselves (P0300, …) are standardised SAE identifiers (facts, not
 * copyrightable); every French/Arabic description below is an original
 * clean-room paraphrase written for this app (MIT).
 */
data class FaultCode(
    val code: String,
    val systemFr: String,
    val titleFr: String,
    val titleAr: String,
    val adviceFr: String,
    val adviceAr: String
)

object FaultCodeCatalog {
    val codes: List<FaultCode> = listOf(
        FaultCode("P0101", "Admission", "Débitmètre d'air — plage incohérente", "حساس تدفق الهواء — قراءة غير منطقية",
            "Nettoyer ou remplacer le débitmètre, vérifier le filtre à air.", "نظّف حساس الهواء أو بدّله وتفقد فلتر الهواء."),
        FaultCode("P0106", "Admission", "Capteur de pression admission — signal anormal", "حساس ضغط السحب — إشارة غير طبيعية",
            "Contrôler durites de dépression et connecteur du capteur MAP.", "افحص خراطيم الهواء ووصلة حساس الضغط."),
        FaultCode("P0113", "Admission", "Température d'air admission trop haute", "حرارة هواء السحب مرتفعة جداً",
            "Vérifier le capteur IAT et son câblage.", "افحص حساس حرارة الهواء والأسلاك."),
        FaultCode("P0115", "Refroidissement", "Température liquide de refroidissement — circuit défaillant", "حرارة سائل التبريد — خلل في الدائرة",
            "Contrôler le capteur, le niveau de liquide et le calorstat.", "افحص الحساس ومستوى سائل التبريد والترموستات."),
        FaultCode("P0118", "Refroidissement", "Température liquide anormalement haute", "حرارة سائل التبريد عالية بشكل غير طبيعي",
            "Arrêter le moteur, vérifier niveau de liquide et ventilateur.", "أوقف المحرك وافحص مستوى السائل والمروحة."),
        FaultCode("P0128", "Refroidissement", "Moteur trop long à chauffer", "المحرك يستغرق وقتاً طويلاً ليسخن",
            "Calorstat probablement bloqué ouvert, à remplacer.", "غالباً الترموستات عالق مفتوحاً ويجب تبديله."),
        FaultCode("P0131", "Échappement", "Sonde lambda amont — tension trop basse", "حساس الأكسجين الأمامي — جهد منخفض",
            "Sonde à remplacer ou fuite d'échappement avant la sonde.", "بدّل الحساس أو ابحث عن تسرب في العادم قبله."),
        FaultCode("P0133", "Échappement", "Sonde lambda amont — réponse lente", "حساس الأكسجين الأمامي — استجابة بطيئة",
            "Sonde vieillissante, prévoir son remplacement.", "الحساس متقادم، خطط لتبديله."),
        FaultCode("P0141", "Échappement", "Chauffage sonde lambda aval en panne", "سخّان حساس الأكسجين الخلفي معطّل",
            "Remplacer la sonde aval.", "بدّل الحساس الخلفي."),
        FaultCode("P0171", "Carburant", "Mélange trop pauvre (rangée 1)", "خليط فقير جداً (الصف 1)",
            "Chercher une prise d'air, vérifier pression de carburant.", "ابحث عن تسرب هواء وافحص ضغط الوقود."),
        FaultCode("P0172", "Carburant", "Mélange trop riche (rangée 1)", "خليط غني جداً (الصف 1)",
            "Vérifier injecteurs, filtre à air encrassé, régulateur de pression.", "افحص البخاخات وفلتر الهواء ومنظم الضغط."),
        FaultCode("P0174", "Carburant", "Mélange trop pauvre (rangée 2)", "خليط فقير جداً (الصف 2)",
            "Même diagnostic que P0171 sur la seconde rangée (V6/V8).", "نفس تشخيص الرمز السابق على الصف الثاني."),
        FaultCode("P0201", "Carburant", "Injecteur cylindre 1 — circuit défaillant", "بخاخ الأسطوانة 1 — خلل في الدائرة",
            "Tester connecteur, câblage puis l'injecteur lui-même.", "افحص الوصلة والأسلاك ثم البخاخ نفسه."),
        FaultCode("P0202", "Carburant", "Injecteur cylindre 2 — circuit défaillant", "بخاخ الأسطوانة 2 — خلل في الدائرة",
            "Tester connecteur, câblage puis l'injecteur lui-même.", "افحص الوصلة والأسلاك ثم البخاخ نفسه."),
        FaultCode("P0203", "Carburant", "Injecteur cylindre 3 — circuit défaillant", "بخاخ الأسطوانة 3 — خلل في الدائرة",
            "Tester connecteur, câblage puis l'injecteur lui-même.", "افحص الوصلة والأسلاك ثم البخاخ نفسه."),
        FaultCode("P0204", "Carburant", "Injecteur cylindre 4 — circuit défaillant", "بخاخ الأسطوانة 4 — خلل في الدائرة",
            "Tester connecteur, câblage puis l'injecteur lui-même.", "افحص الوصلة والأسلاك ثم البخاخ نفسه."),
        FaultCode("P0219", "Moteur", "Surrégime moteur détecté", "تجاوز السرعة القصوى للمحرك",
            "Éviter les surrégimes, vérifier le limiteur.", "تجنب رفع الدوران المفرط وافحص المحدد."),
        FaultCode("P0230", "Carburant", "Pompe à carburant — circuit défaillant", "مضخة الوقود — خلل في الدائرة",
            "Vérifier relais, fusible puis la pompe.", "افحص المرحّل والفيوز ثم المضخة."),
        FaultCode("P0300", "Allumage", "Ratés d'allumage multiples aléatoires", "اختلالات احتراق عشوائية متعددة",
            "Contrôler bougies, bobines et alimentation carburant.", "افحص الشمعات والكويلات وإمداد الوقود."),
        FaultCode("P0301", "Allumage", "Ratés d'allumage cylindre 1", "اختلال احتراق الأسطوانة 1",
            "Permuter bobine/bougie avec un autre cylindre pour isoler la panne.", "بدّل الكويل أو الشمعة مع أسطوانة أخرى لتحديد العطل."),
        FaultCode("P0302", "Allumage", "Ratés d'allumage cylindre 2", "اختلال احتراق الأسطوانة 2",
            "Permuter bobine/bougie avec un autre cylindre pour isoler la panne.", "بدّل الكويل أو الشمعة مع أسطوانة أخرى لتحديد العطل."),
        FaultCode("P0303", "Allumage", "Ratés d'allumage cylindre 3", "اختلال احتراق الأسطوانة 3",
            "Permuter bobine/bougie avec un autre cylindre pour isoler la panne.", "بدّل الكويل أو الشمعة مع أسطوانة أخرى لتحديد العطل."),
        FaultCode("P0304", "Allumage", "Ratés d'allumage cylindre 4", "اختلال احتراق الأسطوانة 4",
            "Permuter bobine/bougie avec un autre cylindre pour isoler la panne.", "بدّل الكويل أو الشمعة مع أسطوانة أخرى لتحديد العطل."),
        FaultCode("P0325", "Allumage", "Capteur de cliquetis — circuit défaillant", "حساس الطرق — خلل في الدائرة",
            "Vérifier le capteur et son serrage au couple.", "افحص الحساس وإحكام ربطه."),
        FaultCode("P0335", "Allumage", "Capteur vilebrequin — pas de signal", "حساس عمود المرفق — لا إشارة",
            "Moteur qui ne démarre pas souvent ; remplacer le capteur PMH.", "غالباً يمنع التشغيل؛ بدّل حساس الكرنك."),
        FaultCode("P0340", "Allumage", "Capteur arbre à cames — circuit défaillant", "حساس عمود الكامات — خلل في الدائرة",
            "Contrôler capteur, distribution et câblage.", "افحص الحساس وسير التوقيت والأسلاك."),
        FaultCode("P0351", "Allumage", "Bobine cylindre 1 — circuit défaillant", "كويل الأسطوانة 1 — خلل في الدائرة",
            "Tester puis remplacer la bobine.", "اختبر الكويل ثم بدّله."),
        FaultCode("P0352", "Allumage", "Bobine cylindre 2 — circuit défaillant", "كويل الأسطوانة 2 — خلل في الدائرة",
            "Tester puis remplacer la bobine.", "اختبر الكويل ثم بدّله."),
        FaultCode("P0401", "Échappement", "Recirculation EGR — débit insuffisant", "صمام تدوير العادم — تدفق ناقص",
            "Décalaminer ou remplacer la vanne EGR.", "نظّف صمام الـEGR من الكربون أو بدّله."),
        FaultCode("P0404", "Échappement", "Vanne EGR — position incohérente", "صمام تدوير العادم — وضعية غير منطقية",
            "Nettoyer la vanne, vérifier son moteur électrique.", "نظّف الصمام وافحص محركه الكهربائي."),
        FaultCode("P0420", "Échappement", "Catalyseur — efficacité insuffisante (rangée 1)", "المحفز — كفاءة ناقصة (الصف 1)",
            "Catalyseur usé ou sonde aval faussée ; diagnostic avant remplacement.", "المحفز متآكل أو الحساس الخلفي مخطئ؛ شخّص قبل التبديل."),
        FaultCode("P0430", "Échappement", "Catalyseur — efficacité insuffisante (rangée 2)", "المحفز — كفاءة ناقصة (الصف 2)",
            "Même diagnostic que P0420 sur la seconde rangée.", "نفس تشخيص الرمز السابق على الصف الثاني."),
        FaultCode("P0442", "Échappement", "Évaporation carburant — petite fuite", "تبخر الوقود — تسرب صغير",
            "Vérifier bouchon de réservoir puis durites EVAP.", "افحص غطاء الخزان ثم خراطيم التبخر."),
        FaultCode("P0446", "Échappement", "Évaporation carburant — évent bloqué", "تبخر الوقود — فتحة التهوية مسدودة",
            "Contrôler l'électrovanne d'évent du canister.", "افحص صمام تهوية علبة الفحم."),
        FaultCode("P0455", "Échappement", "Évaporation carburant — fuite importante", "تبخر الوقود — تسرب كبير",
            "Bouchon manquant ou durite débranchée.", "الغطاء مفقود أو خرطوم مفصول."),
        FaultCode("P0500", "Transmission", "Capteur de vitesse véhicule défaillant", "حساس سرعة السيارة معطّل",
            "Compteur et boîte affectés ; remplacer le capteur.", "يؤثر على العداد وعلبة السرعة؛ بدّل الحساس."),
        FaultCode("P0505", "Ralenti", "Régulation du ralenti défaillante", "التحكم في الخمول معطّل",
            "Nettoyer le papillon et l'actuateur de ralenti.", "نظّف بوابة الهواء وصمام الخمول."),
        FaultCode("P0507", "Ralenti", "Ralenti trop haut", "دوران الخمول مرتفع",
            "Chercher une prise d'air après le papillon.", "ابحث عن تسرب هواء بعد البوابة."),
        FaultCode("P0562", "Électrique", "Tension batterie trop basse", "جهد البطارية منخفض جداً",
            "Tester batterie et alternateur, vérifier les cosses.", "افحص البطارية والدينامو وأقطابها."),
        FaultCode("P0563", "Électrique", "Tension batterie trop haute", "جهد البطارية مرتفع جداً",
            "Régulateur d'alternateur suspect.", "منظم الدينامو مشتبه فيه."),
        FaultCode("P0606", "Calculateur", "Calculateur moteur — défaut interne", "كمبيوتر المحرك — عطل داخلي",
            "Vérifier masses et alimentation avant d'envisager le remplacement.", "افحص التأريض والتغذية قبل التفكير بالتبديل."),
        FaultCode("P0700", "Transmission", "Boîte automatique — défaut signalé", "علبة السرعة الأوتوماتيكية — عطل مبلغ عنه",
            "Lire aussi les codes de la boîte avec un outil compatible.", "اقرأ أيضاً رموز العلبة بأداة متوافقة."),
        FaultCode("P0705", "Transmission", "Capteur position levier — signal incohérent", "حساس وضعية العصا — إشارة غير منطقية",
            "Régler ou remplacer le contacteur de position.", "اضبط مفتاح الوضعية أو بدّله."),
        FaultCode("P0715", "Transmission", "Capteur régime d'entrée boîte défaillant", "حساس سرعة دخل العلبة معطّل",
            "Passages de rapports perturbés ; remplacer le capteur.", "تعشيق مضطرب؛ بدّل الحساس."),
        FaultCode("P0730", "Transmission", "Rapport de boîte incorrect", "نسبة تعشيق غير صحيحة",
            "Niveau d'huile de boîte bas ou embrayages usés.", "مستوى زيت العلبة منخفض أو القوابض متآكلة."),
        FaultCode("P0741", "Transmission", "Verrouillage convertisseur défaillant", "قفل محول العزم معطّل",
            "Surconsommation sur route ; diagnostic de boîte requis.", "استهلاك زائد على الطريق؛ يلزم تشخيص العلبة."),
        FaultCode("P1135", "Échappement", "Chauffage sonde lambda amont en panne", "سخّان حساس الأكسجين الأمامي معطّل",
            "Remplacer la sonde amont.", "بدّل الحساس الأمامي."),
        FaultCode("P1155", "Échappement", "Chauffage sonde lambda — circuit ouvert", "سخّان حساس الأكسجين — دائرة مفتوحة",
            "Vérifier fusible et faisceau de la sonde.", "افحص الفيوز وضفيرة الحساس.")
    )

    /** Case/space-insensitive search over code, titles and system. */
    fun search(query: String): List<FaultCode> {
        val q = query.trim().uppercase().replace(" ", "")
        if (q.isEmpty()) return codes
        return codes.filter {
            it.code.replace(" ", "").contains(q) ||
                it.titleFr.uppercase().contains(query.trim().uppercase()) ||
                it.titleAr.contains(query.trim()) ||
                it.systemFr.uppercase().contains(query.trim().uppercase())
        }
    }
}
