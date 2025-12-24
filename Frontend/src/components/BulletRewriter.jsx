import React, { useState } from 'react';
import { Sparkles, Copy, Check, Loader2, ArrowRight, Target, Cpu } from 'lucide-react';
import { gsap } from 'gsap';

export const BulletRewriter = () => {
    const [originalBullet, setOriginalBullet] = useState('');
    const [targetRole, setTargetRole] = useState('');
    const [focusSkill, setFocusSkill] = useState('');
    const [loading, setLoading] = useState(false);
    const [result, setResult] = useState(null);
    const [copied, setCopied] = useState(false);
    const [error, setError] = useState(null);

    const handleRewrite = async () => {
        if (!originalBullet || !targetRole || !focusSkill) {
            setError('Please fill in all fields to rewrite your bullet.');
            return;
        }

        setLoading(true);
        setError(null);
        setResult(null);

        try {
            const response = await fetch('http://localhost:8080/api/rewrite-bullet', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ originalBullet, targetRole, focusSkill }),
            });

            if (!response.ok) throw new Error('Failed to rewrite bullet. Please try again.');

            const data = await response.json();
            setResult(data);

            // Animate result entry
            gsap.from('.result-card', {
                opacity: 0,
                y: 20,
                duration: 0.6,
                ease: 'power3.out'
            });

        } catch (err) {
            setError(err.message);
        } finally {
            setLoading(false);
        }
    };

    const handleCopy = () => {
        if (result?.rewrittenBullet) {
            navigator.clipboard.writeText(result.rewrittenBullet);
            setCopied(true);
            setTimeout(() => setCopied(false), 2000);
        }
    };

    return (
        <section className="mt-20 p-8 rounded-[2.5rem] bg-surface border border-border relative overflow-hidden">
            {/* Background Decorative Element */}
            <div className="absolute -top-24 -right-24 w-64 h-64 bg-indigo-500/10 rounded-full blur-3xl pointer-events-none" />

            <div className="relative z-10">
                <div className="flex items-center gap-3 mb-8">
                    <div className="w-12 h-12 bg-indigo-500 rounded-2xl flex items-center justify-center text-white shadow-lg shadow-indigo-500/20">
                        <Sparkles size={24} />
                    </div>
                    <div>
                        <h2 className="text-3xl font-bold tracking-tight">AI Bullet Rewriter</h2>
                        <p className="text-slate-500 dark:text-neutral-400">Transform weak points into high-impact STAR bullets.</p>
                    </div>
                </div>

                <div className="grid grid-cols-1 lg:grid-cols-2 gap-12">
                    {/* Input Controls */}
                    <div className="space-y-6">
                        <div className="space-y-2">
                            <label className="text-sm font-semibold uppercase tracking-wider opacity-60 flex items-center gap-2">
                                <Target size={14} /> Target Job Role
                            </label>
                            <input
                                type="text"
                                value={targetRole}
                                onChange={(e) => setTargetRole(e.target.value)}
                                placeholder="e.g. Senior Frontend Engineer"
                                className="w-full p-4 rounded-2xl bg-background border border-border focus:ring-2 focus:ring-indigo-500/20 focus:outline-none transition-all"
                            />
                        </div>

                        <div className="space-y-2">
                            <label className="text-sm font-semibold uppercase tracking-wider opacity-60 flex items-center gap-2">
                                <Cpu size={14} /> Focus Skill / Technology
                            </label>
                            <input
                                type="text"
                                value={focusSkill}
                                onChange={(e) => setFocusSkill(e.target.value)}
                                placeholder="e.g. React & TypeScript"
                                className="w-full p-4 rounded-2xl bg-background border border-border focus:ring-2 focus:ring-indigo-500/20 focus:outline-none transition-all"
                            />
                        </div>

                        <div className="space-y-2">
                            <label className="text-sm font-semibold uppercase tracking-wider opacity-60">Weak Resume Bullet</label>
                            <textarea
                                value={originalBullet}
                                onChange={(e) => setOriginalBullet(e.target.value)}
                                placeholder="e.g. Worked on frontend features..."
                                className="w-full h-32 p-4 rounded-2xl bg-background border border-border focus:ring-2 focus:ring-indigo-500/20 focus:outline-none resize-none transition-all"
                            />
                        </div>

                        {error && <p className="text-rose-500 text-sm font-medium">{error}</p>}

                        <button
                            onClick={handleRewrite}
                            disabled={loading}
                            className="w-full py-4 bg-indigo-500 text-white rounded-2xl font-bold hover:bg-indigo-600 transition-all flex items-center justify-center gap-3 shadow-xl shadow-indigo-500/20"
                        >
                            {loading ? (
                                <><Loader2 className="animate-spin" size={20} /> Optimizing...</>
                            ) : (
                                <><Sparkles size={20} /> Rewrite with STAR Impact</>
                            )}
                        </button>
                    </div>

                    {/* Result Display */}
                    <div className="flex flex-col h-full">
                        {result ? (
                            <div className="result-card flex-1 flex flex-col gap-6">
                                <div className="p-6 rounded-3xl bg-indigo-500/5 border border-indigo-500/20 border-dashed relative group">
                                    <div className="flex justify-between items-start mb-4">
                                        <span className="px-3 py-1 bg-indigo-500 text-white text-[10px] font-bold uppercase tracking-widest rounded-full">
                                            Impact-Focused Rewrite
                                        </span>
                                        <button
                                            onClick={handleCopy}
                                            className="p-2 rounded-xl hover:bg-indigo-500/10 text-indigo-500 transition-colors"
                                            title="Copy to clipboard"
                                        >
                                            {copied ? <Check size={18} /> : <Copy size={18} />}
                                        </button>
                                    </div>
                                    <p className="text-xl font-medium leading-relaxed italic text-foreground/90 font-serif">
                                        "{result.rewrittenBullet}"
                                    </p>
                                </div>

                                <div className="p-6 rounded-3xl bg-background border border-border shadow-sm">
                                    <h4 className="text-sm font-bold uppercase tracking-wider mb-2 text-indigo-500">Why this is better:</h4>
                                    <p className="text-slate-600 dark:text-neutral-400 text-sm leading-relaxed">
                                        {result.whyThisIsBetter}
                                    </p>
                                </div>

                                <div className="mt-auto pt-4 flex items-center gap-4 text-xs font-medium opacity-40 uppercase tracking-widest">
                                    <span>Before</span>
                                    <ArrowRight size={12} />
                                    <span>After</span>
                                </div>
                            </div>
                        ) : (
                            <div className="flex-1 rounded-3xl border-2 border-dashed border-border flex flex-col items-center justify-center text-center p-8 opacity-40">
                                <div className="w-16 h-16 bg-slate-100 dark:bg-neutral-800 rounded-full flex items-center justify-center mb-4">
                                    <Cpu size={32} />
                                </div>
                                <p className="max-w-[200px]">Fill in the details to see your improved resume point.</p>
                            </div>
                        )}
                    </div>
                </div>
            </div>
        </section>
    );
};
